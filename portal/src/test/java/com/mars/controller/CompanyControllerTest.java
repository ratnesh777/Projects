package com.mars.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.AbstractControllerTest;
import com.mars.models.Company;
import com.mars.repository.CompanyRepository;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

public class CompanyControllerTest extends AbstractControllerTest

{

	@Autowired
	CompanyRepository companyRepository;

	@Test
	public void shouldReturnCompanyDetailWhenValidInputsPassedInRequest() throws Exception {

		Company company = new Company();
		company.setName("MARS POLICY");

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.COMPANY_API_END_POINT)
                 .content(objectMapper.writeValueAsString(company)).accept(MediaType.APPLICATION_JSON)
                 .contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                 .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
         
		Company createdCompany = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Company.class);
         //delete to make it other unit test cases consistent
		companyRepository.delete(Integer.parseInt(createdCompany.getId()));
	}
	
	@Test
	public void shouldReturnCreateErrorWhenCompanyNameNotPassedInRequest() throws Exception {
		Company company = new Company();
		company.setId("2000");
		
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.COMPANY_API_END_POINT)
				.content(objectMapper.writeValueAsString(company)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_CREATE_REQUEST)));
	}
	
	@Test
	public void shouldReturnCompanyDetailsWhenValidIdPassedInRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+"/"+ "1001"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("1001")))
				.andExpect(jsonPath("$.name", is("MARS-TEST")));
	}
	
	@Test
	public void shouldReturnCompanyNotFoundErrorWhenInvalidIdPassedInRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "/" + "234"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.COMPANY_NOT_FOUND)));
	}
	
	
	@Test
	public void shouldReturnCompanyNotFoundErrorWhenIdPassedAsStringInRequest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "/" + "ABC234"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.COMPANY_NOT_FOUND)));
	}
	
	
	@Test
	public void shouldReturnAllCompanyList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT)).andDo(print())
				.andExpect(jsonPath("$.totalElements", is(2)))
				.andExpect(jsonPath("$.numberOfElements", is(2)))
				.andExpect(jsonPath("$.totalPages", is(1)))
				.andExpect(status().isOk())
				
				.andExpect(jsonPath("$.content", hasSize(2)))
				
				.andExpect(jsonPath("$.content[0].id", is("1000")))
				.andExpect(jsonPath("$.content[0].name", is("some company")))
				
				.andExpect(jsonPath("$.content[1].id", is("1001")))
				.andExpect(jsonPath("$.content[1].name", is("MARS-TEST")));
	}
	
	@Test
	public void shouldReturnPaginatedComapnyListWhenPageNumberAndSizePassedInRequestAsRequestParameter() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=0&size=1")).andDo(print())
		.andExpect(jsonPath("$.totalElements", is(2)))
		.andExpect(jsonPath("$.numberOfElements", is(1)))
		.andExpect(jsonPath("$.size", is(1)))
		.andExpect(jsonPath("$.totalPages", is(2)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1)))
		
		.andExpect(jsonPath("$.content[0].id", is("1000")))
		.andExpect(jsonPath("$.content[0].name", is("some company")));
	}
	
	@Test
	public void shouldReturnPaginatedDescendingOrderSortedCompanyWhenPageNumberSizeAndSortParamsPassedInRequestAsRequestParameter() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=0&size=1&sortParam=name&sortDirection=desc")).andDo(print())
		.andExpect(jsonPath("$.totalElements", is(2)))
		.andExpect(jsonPath("$.numberOfElements", is(1)))
		.andExpect(jsonPath("$.size", is(1)))
		.andExpect(jsonPath("$.totalPages", is(2)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1)))
		
		.andExpect(jsonPath("$.content[0].id", is("1000")))
		.andExpect(jsonPath("$.content[0].name", is("some company")));
	}
	
	@Test
	public void shouldReturnPaginatedAsscendingOrderSortedCompanyListWhenPageNumberSizeAndSortParamsPassedInRequestAsRequestParameter() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=0&size=1&sortParam=name")).andDo(print())
		.andExpect(jsonPath("$.totalElements", is(2)))
		.andExpect(jsonPath("$.numberOfElements", is(1)))
		.andExpect(jsonPath("$.size", is(1)))
		.andExpect(jsonPath("$.totalPages", is(2)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1)))
		
		.andExpect(jsonPath("$.content[0].id", is("1001")))
		.andExpect(jsonPath("$.content[0].name", is("MARS-TEST")));
	}
	
	@Test
	public void shouldReturnSecondPagePaginatedCompanyListWhenPageNumberAndSizePassedInRequestAsRequestParameter() throws Exception {
	
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=1&size=1")).andDo(print())
		.andExpect(jsonPath("$.totalElements", is(2)))
		.andExpect(jsonPath("$.numberOfElements", is(1)))
		.andExpect(jsonPath("$.size", is(1)))
		.andExpect(jsonPath("$.totalPages", is(2)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content", hasSize(1)))
		
		.andExpect(jsonPath("$.content[0].id", is("1001")))
		.andExpect(jsonPath("$.content[0].name", is("MARS-TEST")));
	}
	
	

	@Test
	public void shouldReturnErrorWhenInvalidPageNumberPassedInRequestAsRequestParameter() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=-1&size=1"))
			.andDo(print()).andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
			.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));
	}
	
	@Test
	public void shouldReturnErrorWhenInvalidSizePassedInRequestAsRequestParameter() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=1&size=0"))
			.andDo(print()).andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
			.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));			
	}
	
	@Test
	public void shouldReturnErrorWhenInvalidSortParamPassedInRequestAsRequestParameter() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=1&size=0&sortParam=name1"))
			.andDo(print()).andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
			.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));			
	}
	
	@Test
	public void shouldReturnErrorWhenInvalidSortDirectionPassedInRequestAsRequestParameter() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT+ "?page=1&size=0&sortParam=name1&sortDirection=ascending"))
			.andDo(print()).andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));			
	}
	
	
}
