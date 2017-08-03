package com.ipc.service;

import java.util.List;

import com.ipc.exception.EmpNotFoundException;
import com.ipc.model.Employee;

public interface EmpService {

    Employee createEmployee(Employee employee);

    Employee findEmployeeById(String id) throws EmpNotFoundException;

    List<Employee> findEmployees();
    
    List<Employee> findEmployeesWithPagination(Integer page);
    
    Employee updateEmployee(Employee employee, String id);
}
