package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.exception.ResourceNotFoundException;
import com.mars.models.Company;


public interface CompanyService {

	Company createCompany(Company company);

	Company findCompanyById(String id) throws ResourceNotFoundException;

	Page<Company> findCompanyList(Integer page, Integer size, String sortParam, String sortDirection);

}
