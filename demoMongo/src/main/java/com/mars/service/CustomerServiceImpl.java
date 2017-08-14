package com.mars.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mongo.repository.CustomerRepository;
import com.mars.mongo.repository.entity.Customer;
import com.mars.util.ErrorMessagesConstant;
import com.querydsl.core.types.Predicate;

@Service
public class CustomerServiceImpl extends AbstractServiceImpl implements CustomerService
{
	Logger auditLog = LogManager.getLogger("auditLogger");

    @Autowired
    CustomerRepository customerRepository;
    

    @Override
    public Customer createCustomer(Customer company)
    {
        company.setId(null);
       // validateCompanyName(company.getName());
        Customer customer=customerRepository.save(company);
        auditLog.info("User create customer successfully  " + company.getId() + " with metadata");
        return customer;
    }

    @Override
    public Customer findCustomerById(String id) throws ResourceNotFoundException
    {
        Customer company = null;
        try
        {
            company = customerRepository.findOne(id);
            auditLog.info("Customer found  " + company.getId() + " with metadata");
        }
        catch (Exception ignored)
        {

        }

        if (company == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.CUSTOMER_NOT_FOUND);

        }
        return company;
    }

    @Override
    public Page<Customer> findCustomerList(Integer page, Integer size, String sortParam,
            String sortDirection,String searchString)
    {
    	
    	if (StringUtils.isNotBlank(searchString))
        {
            //return searchAll(page, size, sortParam, sortDirection, searchString);
        }
    	  auditLog.info("Returned paginated and sorted customer data");
        return customerRepository.findAll(getPageRequest(page, size, sortParam, sortDirection));
    }

    private void validateCompanyName(String name)
    {
        Customer companyEntity = customerRepository.findByName(name);
        if (companyEntity != null)
        {
            throw new PortalServiceParameterException(ErrorMessagesConstant.DUPLICATE_CUSTOMER_NAME);
        }
    }

    @Override
    public Customer updateCustomer(Customer company)
    {
        Customer dbCompany = customerRepository.findOne(company.getId());
        if (dbCompany == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.CUSTOMER_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, company.getId());
        }
        // validate unique name value
        Customer companyRepo = customerRepository.findByName(company.getName());
        if (companyRepo != null && (!companyRepo.getId().equals(company.getId())))
        {
            throw new PortalServiceParameterException(ErrorMessagesConstant.DUPLICATE_CUSTOMER_NAME)
                    .addContextValue("company", company.getName());
        }
        customerRepository.save(company);
        auditLog.info("Customer successfully edited  " + dbCompany.getId() + " with metadata");
        return company;
    }
	
	 @Override
	    public void delete(List<String> ids)
	    {

	        for (String id : ids)
	        {
	        	Customer company = customerRepository.findOne(id);
	        	if(company!=null){
	        		customerRepository.delete(id);
	        		auditLog.info("User successfully deleted a customer " + id + " with metadata");
	        		}
	        }
	    }

	    @Override
	    public void deleteAll()
	    {
	    	List<Customer> companyList=customerRepository.findAll();
	    
	    	customerRepository.deleteAll();
	        auditLog.info("User successfully deleted all customer with metadata");
	    }
	    

	   /* public Page<Customer> searchAll(Integer page, Integer size, String sortParam,
	            String sortDirection, String searchString)
	    {

	        auditLog.info("Returned paginated and sorted customer data by searchString");

	        QCustomer qCustomer=QCustomer.customer;

	        Predicate predicate = qCustomer.name.containsIgnoreCase(searchString)
	                .or(qCustomer.siteID.containsIgnoreCase(searchString))
	                .or(qCustomer.softClientURL.containsIgnoreCase(searchString))
	                .or(qCustomer.ntrURL.containsIgnoreCase(searchString))
	                .or(qCustomer.skypeURL.containsIgnoreCase(searchString))
	                .or(qCustomer.emailDomain.containsIgnoreCase(searchString))
	                .or(qCustomer.ntrServerFQDN.containsIgnoreCase(searchString));
	        return companyRepository.findAll(predicate,
	                getPageRequest(page, size, sortParam, sortDirection))
	          ;
	    }*/
	    
}
