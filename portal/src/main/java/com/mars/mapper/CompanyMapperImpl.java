package com.mars.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.mars.models.Company;
import com.mars.repository.entity.CompanyEntity;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

@Service
public class CompanyMapperImpl extends AbstractBaseMapper<CompanyEntity, Company> implements CompanyMapper {

	@Override
	public Company toModel(CompanyEntity entity) {
		if (entity == null) {
			return null;
		}
		Company company = new Company();
		if (entity.getId() != null) {
			company.setId(entity.getId().toString());
		}
		company.setName(entity.getName());
		return company;
	}

	@Override
	public CompanyEntity toEntity(Company model) {
		if (model == null) {
			return null;
		}
		CompanyEntity companyEntity = new CompanyEntity();
        if (StringUtils.isNumeric(model.getId())) {
            companyEntity.setId(Integer.valueOf(model.getId()));
        }

        companyEntity.setName(model.getName());
		return companyEntity;
	}

}
