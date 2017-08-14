package com.mars.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mars.error.ErrorResource;
import com.mars.exception.InvalidRequestException;
import com.mars.exception.PortalAccessForbiddenException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.models.CustomerPaginationRequestParameters;
import com.mars.mongo.repository.entity.Customer;
import com.mars.service.CustomerService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = APIUtilConstant.COMPANY_API_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = APIUtilConstant.SWAGGER_COMPANY_API)
public class CustomerController
{

    @Value("${base.path.uri}")
    private String BASE_PATH_URI;

    @Autowired
    public CustomerService customerService;


    @ApiOperation(value = "Creates customer", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ErrorMessagesConstant.CREATED, response = Customer.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Customer> createCompany(@RequestBody @Valid Customer company,
            BindingResult bindingResult)
    {
 
     
        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_CREATE_REQUEST,
                    bindingResult);
        }
        company = customerService.createCustomer(company);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",
                BASE_PATH_URI + APIUtilConstant.COMPANY_API_END_POINT + "/" + company.getId());
        return new ResponseEntity<Customer>(company, httpHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get customer  by Id", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Customer.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getCompanyById(
            @ApiParam(name = "id", value = "company id", required = true) @PathVariable("id") String id)
                    throws ResourceNotFoundException
    {
    	
    		return customerService.findCustomerById(id);
    }

    @ApiOperation(value = "Find list of customers", responseContainer = "List", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Customer.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class)})
    @RequestMapping(method = RequestMethod.GET)
    public Page<Customer> getCompanyList(
            @Valid @ModelAttribute() CustomerPaginationRequestParameters paginationRequestParameters,
            BindingResult bindingResult)
    {
    	
        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED,
                    bindingResult);
        }
       
        return customerService.findCustomerList(paginationRequestParameters.getPage(),
                paginationRequestParameters.getSize(), paginationRequestParameters.getSortParam(),
                paginationRequestParameters.getSortDirection(),paginationRequestParameters.getSearchKey());

    }
    
    @ApiOperation(value = "Update customer by Id", response = Customer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Customer.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class)})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Customer updateCompany(
            @RequestBody @Valid Customer company, BindingResult bindingResult,
            @ApiParam(name = "id", value = "company id", required = true) @PathVariable("id") String id)
    {
    	
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED, bindingResult);
        }

        company.setId(id);
        return customerService.updateCustomer(company);
    }
    
    
    @ApiOperation(value = "Deletes customers")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = ErrorMessagesConstant.NO_CONTENT),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteByIds(
            @ApiParam(name = "isDeleteAll", value = "Pass true to delete all customers", required = true) @RequestParam(value = "isDeleteAll", required = true) boolean isDeleteAll,
            @ApiParam(name = "ids", value = "ids of endpoint", required = false) @RequestParam(value = "ids", required = false) String[] ids)
    {
    	
    	
        if (isDeleteAll)
        {
        	customerService.deleteAll();
        }
        else
        {
            if (ids == null || ids.length == 0)
            {
                throw new PortalServiceParameterException(ErrorMessagesConstant.END_POINT_IDS_NULL_NULL_WHEN_ISDELETEALL_PASSED)
                        .addContextValue(ErrorMessagesConstant.IDS, "");
            }

            else
            {
            	customerService.delete(Arrays.asList(ids));
            }
        }
    }
      
    }


