package com.mars.controller;

import static com.mars.util.APIUtilConstant.ROLE_API_END_POINT;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mars.AbstractControllerTest;
import com.mars.util.ErrorMessagesConstant;

public class RoleControllerTest extends AbstractControllerTest
{

    @Test
    public void findRoleById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2))).andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("IPC Operations")));
    }

    @Test
    public void return404ForNotExistingRoleID() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT + "/555")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message",
                        containsString(ErrorMessagesConstant.ROLE_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.message", containsString("id=555")));
    }

    @Test
    public void return404ForNonNumericId() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT + "/abc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message",
                        containsString(ErrorMessagesConstant.ROLE_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.message", containsString("id=abc")));
    }

    @Test
    public void returnFirstPageOfRoles() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT).param("page", "0")
                .param("size", "1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.totalPages", is(4)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(1)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(nullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void returnAllRoles() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.last", is(true))).andExpect(jsonPath("$.size", is(0)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(nullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(4)))
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    public void returnAscSortedRoles() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT).param("page", "0")
                .param("size", "2").param("sortParam", "name").param("sortDirection", "asc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is("3")))
                .andExpect(jsonPath("$.content[0].name", is("Customer Admin")))

                .andExpect(jsonPath("$.content[1].*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].id", is("4")))
                .andExpect(jsonPath("$.content[1].name", is("Customer End User")));
    }

    @Test
    public void returnAscSortedRolesIfSortDirectionIsNull() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ROLE_API_END_POINT).param("page", "0").param("size", "2")
                        .param("sortParam", "name").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is("3")))
                .andExpect(jsonPath("$.content[0].name", is("Customer Admin")))

                .andExpect(jsonPath("$.content[1].*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].id", is("4")))
                .andExpect(jsonPath("$.content[1].name", is("Customer End User")));
    }

    @Test
    public void returnDescSortedRoles() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(ROLE_API_END_POINT).param("page", "0")
                .param("size", "2").param("sortParam", "name").param("sortDirection", "desc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is("2")))
                .andExpect(jsonPath("$.content[0].name", is("Manufacturing")))

                .andExpect(jsonPath("$.content[1].*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].id", is("1")))
                .andExpect(jsonPath("$.content[1].name", is("IPC Operations")));
        ;
    }

    @Test
    public void return422InvalidRequestForWrongSortParam() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ROLE_API_END_POINT).param("page", "0").param("size", "1")
                        .param("sortParam", "wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(
                        "$.fieldErrors[?(@.resource == 'rolePaginationRequestParameters' && @.code == 'Pattern' && @.field == 'sortParam')]")
                                .exists());
    }

    @Test
    public void return422InvalidRequestForWrongSortDirection() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ROLE_API_END_POINT).param("page", "0").param("size", "1")
                        .param("sortDirection", "wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(
                        "$.fieldErrors[?(@.resource == 'rolePaginationRequestParameters' && @.code == 'Pattern' && @.field == 'sortDirection')]")
                                .exists());
    }

}
