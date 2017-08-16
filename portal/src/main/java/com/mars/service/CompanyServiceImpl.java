package com.mars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.CompanyMapper;
import com.mars.models.Company;
import com.mars.repository.CompanyRepository;
import com.mars.repository.entity.CompanyEntity;
import com.mars.util.ErrorMessagesConstant;


@Service
public class CompanyServiceImpl extends AbstractServiceImpl implements CompanyService{

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CompanyMapper companyMapper;

	@Override
	public Company createCompany(Company company) {
		CompanyEntity companyEntity = null;
		company.setId(null);
		validateCompanyName(company.getName());
		companyEntity = companyMapper.toEntity(company);
		companyEntity = companyRepository.save(companyEntity);
		return companyMapper.toModel(companyEntity);
	}

	private void validateCompanyName(String name) {
		CompanyEntity companyEntity = companyRepository.findByName(name);
		if (companyEntity != null ) {
			throw new PortalServiceParameterException(ErrorMessagesConstant.DUPLICATE_COMPANY_NAME );
		}
		
		
	}

	@Override
	public Company findCompanyById(String id) throws ResourceNotFoundException {
		CompanyEntity companyEntity = null;
		try{
			 companyEntity = companyRepository.findOne(Integer.parseInt(id));	
		}catch(Exception e){
			
		}
		
		if (companyEntity == null) {
			throw new ResourceNotFoundException(ErrorMessagesConstant.COMPANY_NOT_FOUND);
					
		}
		return companyMapper.toModel(companyEntity);
	}

	@Override
	public Page<Company> findCompanyList(Integer page, Integer size, String sortParam, String sortDirection) {
        return companyRepository.findAll(getPageRequest(page, size, sortParam, sortDirection))
                .map(companyEntity -> companyMapper.toModel(companyEntity));
    }
}
