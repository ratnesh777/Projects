package com.mars.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mars.AbstractControllerTest;
import com.mars.models.Login;
import com.mars.repository.CompanyRepository;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

public class LoginControllerTest extends AbstractControllerTest

{

	@Autowired
	CompanyRepository companyRepository;

	@Test
	public void shouldReturnLoginDetailWhenValidInputsPassedInRequest() throws Exception {
		Login loginModel = getLoginModel();
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.LOGIN_API_END_POINT+"/authenticate")
                 .content(objectMapper.writeValueAsString(loginModel)).accept(MediaType.APPLICATION_JSON)
                 .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.emailId", is(loginModel.getEmailId())))
		.andExpect(jsonPath("$.role", is("1")))	;
	}
	
	
	@Test
	public void shouldReturnValidationErrorWhenInvalidFormatUserIdPassedInRequest() throws Exception {
		Login loginModel = getLoginModel();
		loginModel.setEmailId("abcd");
		
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.LOGIN_API_END_POINT+"/authenticate")
                .content(objectMapper.writeValueAsString(loginModel)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_CREATE_REQUEST)));
	}
	@Test
	public void shouldReturnAuthenticationErrorWhenInvalidUserIdPassedInRequest() throws Exception {
		Login loginModel = getLoginModel();
		loginModel.setEmailId("abcd@gmail.com");
		
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.LOGIN_API_END_POINT+"/authenticate")
                .content(objectMapper.writeValueAsString(loginModel)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.PORTAL_AUTHENTICATION_EXCEPTION)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_CREDENTIALS)));
	}
	
	@Test
	public void shouldReturnAuthenticationErrorWhenWrongPasswordPassedInRequest() throws Exception {
		Login loginModel = getLoginModel();
		loginModel.setPassword("abcd123");
		
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.LOGIN_API_END_POINT+"/authenticate")
                .content(objectMapper.writeValueAsString(loginModel)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.PORTAL_AUTHENTICATION_EXCEPTION)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_CREDENTIALS)));
	}
	
	@Test
	public void shouldReturnForbiddenErrorWhenUserIdStatusIsNotRegistered() throws Exception {
		Login loginModel = getLoginModel();
		loginModel.setEmailId("test@mars.com");
		
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.LOGIN_API_END_POINT+"/authenticate")
                .content(objectMapper.writeValueAsString(loginModel)).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isForbidden())
				.andExpect(jsonPath("$.code", is(ErrorMessagesConstant.PORTAL_ACESS_FORBIDEEN_EXCEPTION)))
				.andExpect(jsonPath("$.message", is(ErrorMessagesConstant.ACCESS_FORBIDDEN)));
	}

	@Test
	public void shouldSendEmailTouser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.LOGIN_API_END_POINT+"/sendEmail")
                .content(objectMapper.writeValueAsString("test@mars.com")).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

}
