package com.ipc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipc.exception.EmpNotFoundException;
import com.ipc.mapper.EmpMapper;
import com.ipc.model.Employee;
import com.ipc.repository.EmpEntity;
import com.ipc.repository.EmpPaginationRepository;
import com.ipc.repository.EmpRepository;
import com.ipc.userpoc.model.EmpUserPOCRepository;
import com.ipc.userpoc.model.EmployeePOCTest;

@Service
public class EmpServiceImpl implements EmpService {

	@Autowired
	EmpRepository empRepository;

	@Autowired
	EmpMapper empMapper;
	
	@Autowired
	EmpPaginationRepository empPaginationRepository;

	
	//@Autowired
	//EmpUserPOCRepository empUserPOCRepository;
	
	
	@Override
	@Transactional(value="entityTransactionManager") //TODO either give transcation manager name or make one of them as Primary
	//@Transactional
	public Employee createEmployee(Employee employee) {

		EmpEntity empEntity = empMapper.toEntity(employee);
		empEntity = empRepository.save(empEntity);
 
	/*	EmployeePOCTest employeePOCTest = new EmployeePOCTest();
		employeePOCTest.setName(empEntity.getName());
		employeePOCTest.setSex(empEntity.getSex());
		employeePOCTest = empUserPOCRepository.save(employeePOCTest);*/
		return empMapper.fromEntity(empEntity);

	}

	@Override
	public Employee findEmployeeById(String id) throws EmpNotFoundException {
		EmpEntity empEntity = null;
		try {
			empEntity = empRepository.findOne(Integer.parseInt(id));
		} catch (Exception e) {

		}
		if (empEntity == null) {
			throw new EmpNotFoundException("Employee with id " + id +" not found");
		}
		return empMapper.fromEntity(empEntity);
	}

	@Override
	public List<Employee> findEmployees() {
		List<EmpEntity> empEntitylist = (List<EmpEntity>) empRepository.findAll();
		List<Employee> employees = new ArrayList<Employee>(empEntitylist.size());
		for (EmpEntity empEntity : empEntitylist) {
			employees.add(empMapper.fromEntity(empEntity));
		}
		return employees;

	}

	@Override
	public Employee updateEmployee(Employee employee, String empId) {

		employee.setId(empId);
		EmpEntity empEntity = empMapper.toEntity(employee);
		empEntity = empRepository.save(empEntity);
		return empMapper.fromEntity(empEntity);

	}

	@Override
	public List<Employee> findEmployeesWithPagination(Integer page) {
		
		
		PageRequest pageableRequest = new PageRequest(page, 10); //page,size
	
		Page<EmpEntity> empEntitylist = empPaginationRepository.findAll(pageableRequest);
		
		List<Employee> employees = new ArrayList<Employee>(empEntitylist.getSize());
		for (EmpEntity empEntity : empEntitylist) {
			employees.add(empMapper.fromEntity(empEntity));
		}
		return employees;
	}

}
