package com.mars.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.AbstractControllerTest;
import com.mars.DBLoader;
import com.mars.mongo.repository.CustomerRepository;
import com.mars.mongo.repository.entity.Customer;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;


public class CompanyControllerTest extends AbstractControllerTest

{

    @Autowired
    CustomerRepository companyRepository;
    
    @Autowired
    DBLoader dbLoader;

    @Test
    public void shouldReturnCompanyDetailWhenValidInputsPassedInRequest() throws Exception
    {

        Customer company = new Customer();
        company.setName("IPC POLICY");
        company.setEmailDomain("@ipc.com");
        

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(APIUtilConstant.COMPANY_API_END_POINT)
                        .content(objectMapper.writeValueAsString(company))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        Customer createdCompany = new ObjectMapper()
                .readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        // delete to make it other unit test cases consistent
        companyRepository.delete(createdCompany.getId());
    }

    @Test
    public void shouldReturnCreateErrorWhenCompanyNameNotPassedInRequest() throws Exception
    {
        Customer company = new Customer();
        company.setId("2000");
        company.setEmailDomain("@ipc.com");
        

        mockMvc.perform(MockMvcRequestBuilders.post(APIUtilConstant.COMPANY_API_END_POINT)
                .content(objectMapper.writeValueAsString(company))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message", is(ErrorMessagesConstant.INVALID_CREATE_REQUEST)));
        
    }

    @Test
    public void shouldReturnCompanyDetailsWhenValidIdPassedInRequest() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT + "/" + "1001"))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is("1001")))
                .andExpect(jsonPath("$.name", is("TEST")));
    }

    @Test
    public void shouldReturnCompanyNotFoundErrorWhenInvalidIdPassedInRequest() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT + "/" + "234"))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message", is(ErrorMessagesConstant.CUSTOMER_NOT_FOUND)));
    }

    @Test
    public void shouldReturnCompanyNotFoundErrorWhenIdPassedAsStringInRequest() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT + "/" + "ABC234"))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message", is(ErrorMessagesConstant.CUSTOMER_NOT_FOUND)));
    }

    @Test
    public void shouldReturnAllCompanyList() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT))
                .andDo(print()).andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1))).andExpect(status().isOk())

                .andExpect(jsonPath("$.content", hasSize(2)))

                //.andExpect(jsonPath("$.content[0].id", is("1000")))
                .andExpect(jsonPath("$.content[0].name", is("some company")))

                .andExpect(jsonPath("$.content[1].id", is("1001")))
                .andExpect(jsonPath("$.content[1].name", is("TEST")));
    }

    @Test
    public void shouldReturnPaginatedComapnyListWhenPageNumberAndSizePassedInRequestAsRequestParameter()
            throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders
                .get(APIUtilConstant.COMPANY_API_END_POINT + "?page=0&size=1")).andDo(print())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.size", is(1))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(1)))

                .andExpect(jsonPath("$.content[0].id", is("1000")))
                .andExpect(jsonPath("$.content[0].name", is("some company")));
    }

    @Test
    public void shouldReturnPaginatedDescendingOrderSortedCompanyWhenPageNumberSizeAndSortParamsPassedInRequestAsRequestParameter()
            throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT
                + "?page=0&size=1&sortParam=name&sortDirection=desc")).andDo(print())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.size", is(1))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(1)))

                .andExpect(jsonPath("$.content[0].id", is("1000")))
                .andExpect(jsonPath("$.content[0].name", is("some company")));
    }

    @Test
    public void shouldReturnPaginatedAsscendingOrderSortedCompanyListWhenPageNumberSizeAndSortParamsPassedInRequestAsRequestParameter()
            throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                .get(APIUtilConstant.COMPANY_API_END_POINT + "?page=0&size=1&sortParam=name"))
                .andDo(print()).andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.size", is(1))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(1)))

                .andExpect(jsonPath("$.content[0].id", is("1001")))
                .andExpect(jsonPath("$.content[0].name", is("TEST")));
    }

    @Test
    @Ignore
    public void shouldReturnSecondPagePaginatedCompanyListWhenPageNumberAndSizePassedInRequestAsRequestParameter()
            throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders
                .get(APIUtilConstant.COMPANY_API_END_POINT + "?page=1&size=1")).andDo(print())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.size", is(1))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(1)))

                .andExpect(jsonPath("$.content[0].id", is("1001")))
                .andExpect(jsonPath("$.content[0].name", is("TEST")));
    }

    @Test
    public void shouldReturnErrorWhenInvalidPageNumberPassedInRequestAsRequestParameter()
            throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders
                .get(APIUtilConstant.COMPANY_API_END_POINT + "?page=-1&size=1")).andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST))).andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));
    }

    @Test
    public void shouldReturnErrorWhenInvalidSizePassedInRequestAsRequestParameter() throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders
                .get(APIUtilConstant.COMPANY_API_END_POINT + "?page=1&size=0")).andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST))).andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));
    }

    @Test
    public void shouldReturnErrorWhenInvalidSortParamPassedInRequestAsRequestParameter()
            throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders
                .get(APIUtilConstant.COMPANY_API_END_POINT + "?page=1&size=0&sortParam=name1"))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST))).andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));
    }

    @Test
    public void shouldReturnErrorWhenInvalidSortDirectionPassedInRequestAsRequestParameter()
            throws Exception
    {

        mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT
                + "?page=1&size=0&sortParam=name1&sortDirection=ascending")).andDo(print())
                .andExpect(status().isUnprocessableEntity()).andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)));
    }
    
   
    
    @Test
    @Ignore
    public void shouldThrownprocessableEntityWheninvalidURLPassedInRequest() throws Exception
    {

        Customer company = new Customer();
        company.setName("MARS POLICY");
        company.setEmailDomain("@mars.com");
      

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(APIUtilConstant.COMPANY_API_END_POINT)
                        .content(objectMapper.writeValueAsString(company))
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity()).andReturn();

       
    }
    @Test
    public void shouldDeleteCustomersWhenValidIdPassedInRequest() throws Exception
    {
        Customer company = getCompanyModel();
        companyRepository.save(company);
        
        mockMvc.perform(MockMvcRequestBuilders.delete(APIUtilConstant.COMPANY_API_END_POINT + "?isDeleteAll=false&ids=" + company.getId())
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
        dbLoader.addCustomers();
     
    }
    
    @Test
    public void shouldReturnPortalServiceParameterExceptioneWhenidsNotPassedInRequest() throws Exception
    {

            mockMvc.perform(MockMvcRequestBuilders.delete(APIUtilConstant.COMPANY_API_END_POINT + "?isDeleteAll=false&ids=")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.message", is(ErrorMessagesConstant.END_POINT_IDS_NULL_NULL_WHEN_ISDELETEALL_PASSED)));
    }
   
    
    @Test
    public void shouldDeleteAllCustomersWhenValidIdPassedInRequest() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete(APIUtilConstant.COMPANY_API_END_POINT + "?isDeleteAll=true")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
        dbLoader.addCustomers();
    }
    
    @Test
    @Ignore
    public void searchCompany() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT)
                .param("searchKey", "IPC-TEST").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.last", is(true))).andExpect(jsonPath("$.size", is(0)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(nullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is("1001")))
                .andExpect(jsonPath("$.content[0].name", is("IPC-TEST")));
    }

    
    @Test
    @Ignore
    public void shouldReturnEmptyListWhenSerchKeyNotMatch() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(APIUtilConstant.COMPANY_API_END_POINT)
                .param("searchKey", "Aricent").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.numberOfElements", is(0)))
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content[0].*", hasSize(0)));
    }

}
