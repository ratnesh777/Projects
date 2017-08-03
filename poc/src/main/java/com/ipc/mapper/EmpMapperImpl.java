package com.ipc.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.ipc.model.Employee;
import com.ipc.repository.EmpEntity;

@Service
public class EmpMapperImpl implements EmpMapper {

	@Override
	public Employee fromEntity(EmpEntity empEntity) {

		if(empEntity== null){
			return null;
		}
		Employee emp = new Employee();
		emp.setId(empEntity.getId().toString());
		emp.setName(empEntity.getName());
		emp.setSex(empEntity.getSex());
		return emp;
	}

	@Override
	public EmpEntity toEntity(Employee emp) {

		EmpEntity empEntity = new EmpEntity();
		if(!StringUtils.isEmpty(emp.getId())){
			empEntity.setId(Integer.parseInt(emp.getId()));
		}
			
		empEntity.setName(emp.getName());
		empEntity.setSex(emp.getSex());
		// custom implementation logic depending on requirement
		empEntity.setCreatedBy("Policy Engine APIs");
		return empEntity;
	}

}
