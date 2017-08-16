package com.mars.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mars.repository.entity.CompanyEntity;


@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, Integer> {

	CompanyEntity findByName(String name);
    
   
}
