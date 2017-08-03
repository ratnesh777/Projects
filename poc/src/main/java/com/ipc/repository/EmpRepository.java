package com.ipc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends CrudRepository<EmpEntity, Integer> {

	EmpEntity findByName(String nameSearch);
	
	@Query("select e from EmpEntity e where e.name=:nameSearch")
	EmpEntity findCustomNameSearch(@Param("nameSearch") String nameSearch);
	
	List<EmpEntity> findByCreatedBy(String nameSearch);
	
}
