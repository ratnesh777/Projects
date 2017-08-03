package com.ipc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpPaginationRepository extends PagingAndSortingRepository<EmpEntity, Integer> {

	//EmpEntity findAll(String nameSearch);
	
	
	
}
