package com.ipc.mapper;

import com.ipc.model.Employee;
import com.ipc.repository.EmpEntity;

public interface EmpMapper {

	Employee fromEntity(EmpEntity empEntity);
	
	EmpEntity toEntity(Employee emp);
}
