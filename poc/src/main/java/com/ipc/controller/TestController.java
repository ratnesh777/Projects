package com.ipc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipc.exception.EmpNotFoundException;
import com.ipc.exception.InvalidRequestException;
import com.ipc.model.Employee;


//@EnableWebMvc
@RestController
public class TestController {

	/* public static final String REQUEST_MAPPING = "http://localhost:8080/poc/api/emp"; //hardcoded will updated later
	 
	//@CrossOrigin
	@RequestMapping(value = "/api/emp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getName() {

		
		return createEmployees();
	}
	
	
	@RequestMapping(value = "/api/emp/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getNameById(@PathVariable String id) throws EmpNotFoundException {
		Employee emp = new Employee();
		if(id.equals("1")){
			emp.setId(1);
			emp.setName("John Doe");
		} else{
			throw new EmpNotFoundException("Emp with given id not found");
		}
		return emp;
	}


	@CrossOrigin
	@RequestMapping(value = "/api/emp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
		            throw new InvalidRequestException("Invalid create request", bindingResult);
		}
		
		Employee emp = new Employee();
		emp.setId(8); // hardocded
		emp.setName(employee.getName());
		emp.setSex(employee.getSex());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", REQUEST_MAPPING + "/" + emp.getId());
	    return new ResponseEntity<Employee>(emp, httpHeaders, HttpStatus.CREATED);
		
	}

	private static List<Employee> createEmployees() {
		List<Employee> employees = new ArrayList<Employee>();

		Employee emp = new Employee();
		emp.setId(1);
		emp.setName("John Doe");
		emp.setSex("M");

		Employee emp1 = new Employee();
		emp1.setId(2);
		emp1.setName("Jane Doe");
		emp1.setSex("M");

		Employee emp2 = new Employee();
		emp2.setId(3);
		emp2.setName("Tom Doe");
		emp2.setSex("M");
		
		Employee emp3 = new Employee();
		emp3.setId(4);
		emp3.setName("Tim Hanes");
		emp3.setSex("M");
		
		Employee emp4 = new Employee();
		emp4.setId(5);
		emp4.setName("Tom Hanes");
		emp4.setSex("M");
		
		Employee emp5 = new Employee();
		emp5.setId(6);
		emp5.setName("Jeff Hanes");
		emp5.setSex("M");
		
		Employee emp6 = new Employee();
		emp6.setId(7);
		emp6.setName("Kim Hanes");
		emp6.setSex("F");
		
		employees.add(emp);
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		employees.add(emp4);
		employees.add(emp5);
		employees.add(emp6);

		return employees;
	}*/

}
