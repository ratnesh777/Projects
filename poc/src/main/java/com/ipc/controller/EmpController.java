package com.ipc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipc.exception.EmpNotFoundException;
import com.ipc.exception.InvalidRequestException;
import com.ipc.model.Employee;
import com.ipc.service.EmpService;
import com.ipc.util.APIUtilConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value=APIUtilConstant.EMP_API_END_POINT,produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Emp APIs Detail")
public class EmpController {

	@Value("${base.path.uri}")
	private String BASE_PATH_URI;
	
	@Autowired
	public EmpService empService;
	
    @ApiOperation(nickname = "Create Employee", value = "Creates an Employee", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Employee.class),
            @ApiResponse(code = 422, message = "Invalid Request"),
            @ApiResponse(code = 500, message = "Failure") ,
    		@ApiResponse(code = 400, message = "Bad Request")}) 
	
	  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid create request", bindingResult);
		}
		employee = empService.createEmployee(employee);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", BASE_PATH_URI+ APIUtilConstant.EMP_API_END_POINT+ "/" + employee.getId());
		return new ResponseEntity<Employee>(employee, httpHeaders, HttpStatus.CREATED);
	}
	
    @ApiOperation(nickname = "Retrieves Employee detail by id", value = "Retrieves Employee detail by id", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Employee.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Employee getNameById(@ApiParam(name="empId", value="The Id of the Employee to be viewed", required=true)@PathVariable String id) throws EmpNotFoundException {
		
		return empService.findEmployeeById(id);
	}
	
    @ApiOperation(nickname = "Update Employee", value = "Update Employee", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Employee.class),
            @ApiResponse(code = 422, message = "Invalid Request"),
            @ApiResponse(code = 500, message = "Failure") ,
    		@ApiResponse(code = 400, message = "Bad Request")}) 
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee updateEmp(@ApiParam(name="id", value="The Id of the Employee to be viewed", required=true)  @PathVariable String id,@RequestBody @Valid Employee employee, BindingResult bindingResult) throws EmpNotFoundException {
	
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid create request", bindingResult);
		}
		return empService.updateEmployee(employee,id);
	}
	
	  /*	@RequestMapping(method = RequestMethod.GET)
	public List<Employee> getEmpList() {
		return empService.findEmployees();
	}*/
	
    @ApiOperation(nickname = "Retrieves Employee detail", value = "Retrieves Employee detail ", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Employee.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(method = RequestMethod.GET)
	public List<Employee> getEmpListUsingPagination(@RequestParam(value= "page", required=false) Integer page) {
		if(page!=null) {
			return empService.findEmployeesWithPagination(page);
		} else{
			return empService.findEmployees();
		}
	}
	
}
