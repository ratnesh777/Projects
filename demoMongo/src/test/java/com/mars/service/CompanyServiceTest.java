package com.mars.service;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.mars.AbstractTest;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mongo.repository.CustomerRepository;
import com.mars.mongo.repository.entity.Customer;
import com.mars.util.ErrorMessagesConstant;
import com.querydsl.core.types.Predicate;


public class CompanyServiceTest extends AbstractTest

{
    @Mock
    CustomerRepository companyRepository;
    
     @InjectMocks
    CustomerServiceImpl companyService;
    


    

    @Test
    public void shouldCreateCompanyWhenValidInputsPassedInRequest() throws Exception
    {
        Customer companyModel = getCompanyModel();
        when(companyRepository.findByName(companyModel.getName())).thenReturn(null);
        ArgumentCaptor<Customer> customerEntityCaptor = ArgumentCaptor.forClass(Customer.class);
        companyService.createCustomer(companyModel);
        verify(companyRepository).save(customerEntityCaptor.capture());
        
    }

    @Test
    @Ignore
    public void shouldThrowServiceParameterExceptionWhenDuplicateNamePassed() throws Exception
    {
        Customer companyModel = getCompanyModel();
        when(companyRepository.findByName(companyModel.getName())).thenReturn(companyModel);
        thrown.expect(PortalServiceParameterException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.DUPLICATE_CUSTOMER_NAME));
        companyService.createCustomer(companyModel);
    }

    @Test
    public void shouldReturnCompanyByIdValidIdGivenInRequest() throws Exception
    {
        when(companyRepository.findOne("1001")).thenReturn(getCompanyModel());
        companyService.findCustomerById("1001");
    }

    @Test
    public void shouldThrowCompanyNotFoundExceptionWhenCompanyEntityReturnedAsNullFromDatabase()
            throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.CUSTOMER_NOT_FOUND));
        when(companyRepository.findOne("1001")).thenReturn(null);
        companyService.findCustomerById("1001");
        verify(companyRepository).findOne("1001");
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void shouldReturnAllCompanyListWhenNoParametersPassedinRequest() throws Exception
    {
        List<Customer> entities = new ArrayList<>();
        Customer entity = getCompanyModel();
        Customer entity1 = new Customer();
        entity1.setId("1000");
        entity1.setName("ABC");
        entities.add(entity);
        entities.add(entity1);

        Page<Customer> pageEntities = new PageImpl<>(entities);
        PageRequest pageableRequest = null;

        when(companyRepository.findAll(pageableRequest)).thenReturn(pageEntities);
        companyService.findCustomerList(0, 0, null, null,"");
        verify(companyRepository).findAll(pageableRequest);
        verifyNoMoreInteractions(companyRepository);
    }
    
    @Test
    public void shouldUpdateCompanyWhenValidDetailsPassed() throws Exception
    {
        Customer  company  = getCompanyModel();
        when(companyRepository.findOne(company.getId())).thenReturn(company);
        when(companyRepository.findByName(company.getName())).thenReturn(null);
        when(companyRepository.save(any(Customer.class))).thenReturn(company);
        companyService.updateCustomer(company);
        verify(companyRepository).findOne(company.getId());
        verify(companyRepository).findByName(company.getName());
        verify(companyRepository).save(company);
    }

    
    @Test
    public void shouldThrowExceptionWhenDuplicateCompanyNamePassedinUpdate() throws Exception
    {
    	Customer  company1  = getCompanyModel();
    	Customer  company  = new Customer();
    	company.setId("1001001");
        company.setName("TEST");
        company.setEmailDomain("@mars.com");
    
        thrown.expect(PortalServiceParameterException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.DUPLICATE_CUSTOMER_NAME));
        when(companyRepository.findOne("1001")).thenReturn(company1);
        when(companyRepository.findByName("Mars-TEST")).thenReturn(company);
        
        companyService.updateCustomer(company1);
        verify(companyRepository).findOne("1001");
        verify(companyRepository).findByName("Mars-TEST");
        verifyNoMoreInteractions(companyRepository);
    }
    
    @Test
    public void shouldDeleteCompanyByIds() throws Exception
    {
        
        List<String> companyIds = Arrays.asList("1001");
        Customer company = getCompanyModel();
        when(companyRepository.findOne("1001")).thenReturn(company);
        companyService.delete(companyIds);
        verify(companyRepository).delete(company.getId());
        Mockito.verify(companyRepository,times(1)).findOne("1001");
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void shouldDeleteAllCompany() throws Exception
    {
    	Customer  company1  = getCompanyModel();
    	Customer  company  = new Customer();
    	company.setId("1001001");
        company.setName("MARS-TEST");
        company.setEmailDomain("@mars.com");

        List<Customer> companyList=new ArrayList<>();
        companyList.add(company);
        companyList.add(company1);
        when(companyRepository.findAll()).thenReturn(companyList);
    	companyService.deleteAll();
        verify(companyRepository).deleteAll();
        verify(companyRepository).findAll();
        verifyNoMoreInteractions(companyRepository);
    }
    
    @Test
    @Ignore
    public void shouldSearchCompnayBySearchKey() throws Exception{
    	List<Customer> entities = new ArrayList<>();
        Customer entity = getCompanyModel();
        entities.add(entity);

        Page<Customer> pageEntities = new PageImpl<>(entities);
        PageRequest pageableRequest = null;
       String  searchString="TEST";
     /*  QCustomer qCompany=QCustomer.customer;
        Predicate predicate = qCompany.name.containsIgnoreCase(searchString)
                .or(qCompany.siteID.containsIgnoreCase(searchString))
                .or(qCompany.softClientURL.containsIgnoreCase(searchString))
                .or(qCompany.ntrURL.containsIgnoreCase(searchString))
                .or(qCompany.skypeURL.containsIgnoreCase(searchString))
                .or(qCompany.emailDomain.containsIgnoreCase(searchString))
                .or(qCompany.ntrServerFQDN.containsIgnoreCase(searchString));
        when(companyRepository.findAll(predicate,pageableRequest)).thenReturn(pageEntities);
        companyService.findCompanyList(0, 0, null, null,searchString);
        verify(companyRepository).findAll(predicate,pageableRequest);
        verifyNoMoreInteractions(companyRepository);*/
    }
    @Test
    public void shouldSearchCompnayBySearchKeyIfNotFoundReturnEmptyList() throws Exception{
    	List<Customer> entities = new ArrayList<>();

      /*  Page<Customer> pageEntities = new PageImpl<>(entities);
        PageRequest pageableRequest = null;
       String  searchString="Aricent";
       QCustomer qCompany=QCustomer.customer;
        Predicate predicate = qCompany.name.containsIgnoreCase(searchString)
                .or(qCompany.siteID.containsIgnoreCase(searchString))
                .or(qCompany.softClientURL.containsIgnoreCase(searchString))
                .or(qCompany.ntrURL.containsIgnoreCase(searchString))
                .or(qCompany.skypeURL.containsIgnoreCase(searchString))
                .or(qCompany.emailDomain.containsIgnoreCase(searchString))
                .or(qCompany.ntrServerFQDN.containsIgnoreCase(searchString));
        when(companyRepository.findAll(predicate,pageableRequest)).thenReturn(null);
        companyService.findCompanyList(0, 0, null, null,searchString);
        verify(companyRepository).findAll(predicate,pageableRequest);
        verifyNoMoreInteractions(companyRepository);*/
    }
    

}
