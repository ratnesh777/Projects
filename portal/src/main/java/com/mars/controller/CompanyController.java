package com.mars.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.mars.error.ErrorResource;
import com.mars.exception.InvalidRequestException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.models.Company;
import com.mars.models.CompanyPaginationRequestParameters;
import com.mars.service.CompanyService;
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
public class CompanyController {

	@Value("${base.path.uri}")
	private String BASE_PATH_URI;

	@Autowired
	public CompanyService companyService;

	@ApiOperation(value = "Creates company", response = Company.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = ErrorMessagesConstant.CREATED, response = Company.class),
			@ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
			@ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class) })
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Company> createCompany(@RequestBody @Valid Company company, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException(ErrorMessagesConstant.INVALID_CREATE_REQUEST, bindingResult);
		}
		company = companyService.createCompany(company);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", BASE_PATH_URI + APIUtilConstant.COMPANY_API_END_POINT + "/" + company.getId());
		return new ResponseEntity<Company>(company, httpHeaders, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get company  by Id", response = Company.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Company.class),
			@ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
			@ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Company getCompanyById(
			@ApiParam(name = "id", value = "company id", required = true) @PathVariable("id") String id)
			throws ResourceNotFoundException {
		return companyService.findCompanyById(id);

	}

	@ApiOperation(value = "Find list of company", responseContainer = "List", response = Company.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Company.class),
			@ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class),
			@ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class) })

	@RequestMapping(method = RequestMethod.GET)
	public Page<Company> getCompanyList(
			@Valid @ModelAttribute() CompanyPaginationRequestParameters paginationRequestParameters,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED, bindingResult);
		}

		return companyService.findCompanyList(paginationRequestParameters.getPage(),
				paginationRequestParameters.getSize(), paginationRequestParameters.getSortParam(),
				paginationRequestParameters.getSortDirection());

	}

}
