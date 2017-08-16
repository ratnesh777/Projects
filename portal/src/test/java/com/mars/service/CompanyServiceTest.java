package com.mars.service;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.mars.AbstractTest;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.CompanyMapperImpl;
import com.mars.models.Company;
import com.mars.repository.CompanyRepository;
import com.mars.repository.entity.CompanyEntity;
import com.mars.service.CompanyServiceImpl;
import com.mars.util.ErrorMessagesConstant;

public class CompanyServiceTest extends AbstractTest

{
	@Mock
	CompanyRepository companyRepository;

	@InjectMocks
	CompanyServiceImpl companyService;

	@Spy
	CompanyMapperImpl companyMapper;

	@Test
	public void shouldCreateCompanyWhenValidInputsPassedInRequest() throws Exception {
		Company companyModel = getCompanyModel();
	     when(companyRepository.findByName(companyModel.getName())).thenReturn(null);
		companyService.createCompany(companyModel);
		verify(companyMapper).toEntity(companyModel);
	}
	
    @Test
    public void shouldThrowServiceParameterExceptionWhenDuplicateNamePassed()
            throws Exception
    {
        Company companyModel = getCompanyModel();
        when(companyRepository.findByName(companyModel.getName())).thenReturn(getCompanyEntity());
        thrown.expect(PortalServiceParameterException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.DUPLICATE_COMPANY_NAME));
        companyService.createCompany(companyModel);
    }

    @Test
    public void shouldReturnCompanyByIdValidIdGivenInRequest() throws Exception
    {
    	CompanyEntity entity = getCompanyEntity();
        when(companyRepository.findOne(1001)).thenReturn(entity);
        companyService.findCompanyById("1001");
        verify(companyMapper).toModel(entity);
    }

    @Test
    public void shouldThrowCompanyNotFoundExceptionWhenPolicyEntityReturnedAsNullFromDatabase() throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString(ErrorMessagesConstant.COMPANY_NOT_FOUND));
        when(companyRepository.findOne(1001)).thenReturn(null);
        companyService.findCompanyById("1001");
        verify(companyRepository).findOne(1001);
        verifyNoMoreInteractions(companyRepository);
    }
   
    @Test
    public void shouldReturnAllCompanyListWhenNoParametersPassedinRequest() throws Exception
    {
    	List<CompanyEntity> entities = new ArrayList<CompanyEntity>();
    	CompanyEntity entity = getCompanyEntity();
        CompanyEntity entity1 = new CompanyEntity();
        entity1.setId(1000);
        entity1.setName("ABC");
        entities.add(entity);
        entities.add(entity1);
        
        Page<CompanyEntity> pageEntities = new PageImpl<CompanyEntity>(entities);
        PageRequest pageableRequest = null;
        
        when(companyRepository.findAll(pageableRequest)).thenReturn(pageEntities);
        companyService.findCompanyList(0, 0, null, null);
        verify(companyRepository).findAll(pageableRequest);
        verifyNoMoreInteractions(companyRepository);
    }
    
}
