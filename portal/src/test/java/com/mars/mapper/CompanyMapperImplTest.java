package com.mars.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.mars.mapper.CompanyMapper;
import com.mars.mapper.CompanyMapperImpl;
import com.mars.models.Company;
import com.mars.repository.entity.CompanyEntity;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class CompanyMapperImplTest{

	CompanyMapper companyMapper = new CompanyMapperImpl();

    @Test
    public void shouldReturnNullIfNullEntityPassed() throws Exception
    {
        assertNull(companyMapper.toModel(null));
    }

    @Test
    public void shouldReturnNullIfNullModelPassed() throws Exception
    {
        assertNull(companyMapper.toEntity(null));
    }

    @Test
    public void shouldReturnModelIfNotNullEntityPassed() throws Exception
    {
        CompanyEntity companyEntity = getCompanyEntity();
        assertThat(companyMapper.toModel(companyEntity), new ReflectionEquals(getCompanyModel()));

    }

    @Test
    public void shouldReturnEntityIfNotNullModelPassed() throws Exception
    {
    	Company company = getCompanyModel();
        assertThat(companyMapper.toEntity(company), new ReflectionEquals(getCompanyEntity()));
    }

  
    private CompanyEntity getCompanyEntity()
    {
    	CompanyEntity companyEntity = new CompanyEntity();
    	companyEntity.setId(1001);
    	companyEntity.setName("IPC-TEST");
        return companyEntity;

    }

    private Company getCompanyModel()
    {
    	Company company = new Company();
    	company.setId("1001");
    	company.setName("IPC-TEST");
        return company;
    }

}