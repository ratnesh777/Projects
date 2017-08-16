package com.mars.controller;

import static com.mars.util.APIUtilConstant.USER_API_END_POINT;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mars.AbstractControllerTest;
import com.mars.models.User;
import com.mars.util.ErrorMessagesConstant;

public class UserControllerTest extends AbstractControllerTest
{

    @Test
    public void createUser() throws Exception
    {
        User userModel = composeUserModel();
        userModel.setEmail("some@email.com");

        ResultActions resultActions = createUser(userModel);
        resultActions.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.firstName", is("FN")))
                .andExpect(jsonPath("$.lastName", is("LN")))
                .andExpect(jsonPath("$.email", is("some@email.com")))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.siteId", is("siteId")))
                .andExpect(jsonPath("$.role.*", hasSize(2)))
                .andExpect(jsonPath("$.role.id", is("2")))
                .andExpect(jsonPath("$.role.name", is("Manufacturing")))
                .andExpect(jsonPath("$.company.*", hasSize(2)))
                .andExpect(jsonPath("$.company.id", is("1000")))
                .andExpect(jsonPath("$.company.name", is("some company")));
        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String id = objectMapper.readValue(response.getContentAsByteArray(), User.class).getId();
        assertEquals("http://localhost:8080/portal" + USER_API_END_POINT + "/" + id,
                response.getRedirectedUrl());
    }

    @Test
    public void createUserFailedIfRequiredFieldsAreEmpty() throws Exception
    {
        User userModel = composeUserModel();
        userModel.setFirstName(" ");
        userModel.setLastName(" ");
        userModel.setEmail(" ");
        userModel.setRole(null);
        userModel.setCompany(null);

        ResultActions resultActions = createUser(userModel);
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Invalid create request")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(6)))
                .andExpect(jsonPath(getNotBlankErrorExpression("user", "firstName")).exists())
                .andExpect(jsonPath(getNotBlankErrorExpression("user", "lastName")).exists())
                .andExpect(jsonPath(getNotBlankErrorExpression("user", "email")).exists())
                .andExpect(jsonPath(getNotValidEmailErrorExpression("user", "email")).exists())
                .andExpect(jsonPath(getNotNullErrorExpression("user", "role")).exists())
                .andExpect(jsonPath(getNotNullErrorExpression("user", "company")).exists());
    }

    @Test
    public void createUserFailedIfRequiredFieldsAreNull() throws Exception
    {
        User userModel = composeUserModel();
        userModel.setFirstName(null);
        userModel.setLastName(null);
        userModel.setEmail(null);
        userModel.setRole(null);
        userModel.setCompany(null);

        ResultActions resultActions = createUser(userModel);
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Invalid create request")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(5)))
                .andExpect(jsonPath(getNotBlankErrorExpression("user", "firstName")).exists())
                .andExpect(jsonPath(getNotBlankErrorExpression("user", "lastName")).exists())
                .andExpect(jsonPath(getNotBlankErrorExpression("user", "email")).exists())
                .andExpect(jsonPath(getNotNullErrorExpression("user", "role")).exists())
                .andExpect(jsonPath(getNotNullErrorExpression("user", "company")).exists());
    }

    @Test
    public void return422ForDuplicatedEmail() throws Exception
    {
        ResultActions resultActions = createUser(composeUserModel());
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Parameters passed in the request is invalid.")))
                .andExpect(jsonPath("$.message", is("User email already exist")))
                .andExpect(jsonPath("$.errorContext", hasSize(1)))
                .andExpect(jsonPath(getContextItem("email", "test@ipc.com")).exists());
    }

    @Test
    public void return422ForWrongEmail() throws Exception
    {
        User userModel = composeUserModel();
        userModel.setEmail("wrong email");
        ResultActions resultActions = createUser(userModel);
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Invalid create request")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(getNotValidEmailErrorExpression("user", "email")).exists());
    }

    @Test
    public void findUserById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(8))).andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.firstName", is("FN1")))
                .andExpect(jsonPath("$.lastName", is("LN")))
                .andExpect(jsonPath("$.email", is("test@ipc.com")))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.siteId", is("site id")))
                .andExpect(jsonPath("$.role.*", hasSize(2)))
                .andExpect(jsonPath("$.role.id", is("1")))
                .andExpect(jsonPath("$.role.name", is("IPC Operations")))
                .andExpect(jsonPath("$.company.*", hasSize(2)))
                .andExpect(jsonPath("$.company.id", is("1000")))
                .andExpect(jsonPath("$.company.name", is("some company")));
    }

    @Test
    public void return404ForNotExistingUserID() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT + "/555")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message",
                        containsString(ErrorMessagesConstant.USER_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.message", containsString("id=555")));
    }

    @Test
    public void return404ForNonNumericId() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT + "/abc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message",
                        containsString(ErrorMessagesConstant.USER_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.message", containsString("id=abc")));
    }

    @Test
    public void returnFirstPageOfUsers() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT).param("page", "0")
                .param("size", "1").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.totalPages", is(3)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(1)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(nullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void returnAllUsers() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(true))).andExpect(jsonPath("$.size", is(0)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(nullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(3)))
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    public void returnAscSortedUsers() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT).param("page", "0")
                .param("size", "2").param("sortParam", "firstName").param("sortDirection", "asc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(8)))
                .andExpect(jsonPath("$.content[0].id", is("1")))
                .andExpect(jsonPath("$.content[0].firstName", is("FN1")))
                .andExpect(jsonPath("$.content[0].lastName", is("LN")))
                .andExpect(jsonPath("$.content[0].email", is("test@ipc.com")))
                .andExpect(jsonPath("$.content[0].status", is("CREATED")))
                .andExpect(jsonPath("$.content[0].siteId", is("site id")))
                .andExpect(jsonPath("$.content[0].role.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].role.id", is("1")))
                .andExpect(jsonPath("$.content[0].role.name", is("IPC Operations")))
                .andExpect(jsonPath("$.content[0].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].company.id", is("1000")))
                .andExpect(jsonPath("$.content[0].company.name", is("some company")))

                .andExpect(jsonPath("$.content[1].*", hasSize(8)))
                .andExpect(jsonPath("$.content[1].id", is("3")))
                .andExpect(jsonPath("$.content[1].firstName", is("FN2")))
                .andExpect(jsonPath("$.content[1].lastName", is("LN")))
                .andExpect(jsonPath("$.content[1].email", is("test@gmail.com")))
                .andExpect(jsonPath("$.content[1].status", is("REGISTERED")))
                .andExpect(jsonPath("$.content[1].siteId", is("site id")))
                .andExpect(jsonPath("$.content[1].role.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].role.id", is("1")))
                .andExpect(jsonPath("$.content[1].role.name", is("IPC Operations")))
                .andExpect(jsonPath("$.content[1].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].company.id", is("1000")))
                .andExpect(jsonPath("$.content[1].company.name", is("some company")));
    }

    @Test
    public void returnAscSortedUsersIfSortDirectionIsNull() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(USER_API_END_POINT).param("page", "0").param("size", "2")
                        .param("sortParam", "firstName").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(8)))
                .andExpect(jsonPath("$.content[0].id", is("1")))
                .andExpect(jsonPath("$.content[0].firstName", is("FN1")))
                .andExpect(jsonPath("$.content[0].lastName", is("LN")))
                .andExpect(jsonPath("$.content[0].email", is("test@ipc.com")))
                .andExpect(jsonPath("$.content[0].status", is("CREATED")))
                .andExpect(jsonPath("$.content[0].siteId", is("site id")))
                .andExpect(jsonPath("$.content[0].role.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].role.id", is("1")))
                .andExpect(jsonPath("$.content[0].role.name", is("IPC Operations")))
                .andExpect(jsonPath("$.content[0].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].company.id", is("1000")))
                .andExpect(jsonPath("$.content[0].company.name", is("some company")))

                .andExpect(jsonPath("$.content[1].*", hasSize(8)))
                .andExpect(jsonPath("$.content[1].id", is("3")))
                .andExpect(jsonPath("$.content[1].firstName", is("FN2")))
                .andExpect(jsonPath("$.content[1].lastName", is("LN")))
                .andExpect(jsonPath("$.content[1].email", is("test@gmail.com")))
                .andExpect(jsonPath("$.content[1].status", is("REGISTERED")))
                .andExpect(jsonPath("$.content[1].siteId", is("site id")))
                .andExpect(jsonPath("$.content[1].role.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].role.id", is("1")))
                .andExpect(jsonPath("$.content[1].role.name", is("IPC Operations")))
                .andExpect(jsonPath("$.content[1].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].company.id", is("1000")))
                .andExpect(jsonPath("$.content[1].company.name", is("some company")));
    }

    @Test
    public void returnDescSortedUsers() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(USER_API_END_POINT).param("page", "0")
                .param("size", "2").param("sortParam", "firstName").param("sortDirection", "desc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(8)))
                .andExpect(jsonPath("$.content[0].id", is("2")))
                .andExpect(jsonPath("$.content[0].firstName", is("FN3")))
                .andExpect(jsonPath("$.content[0].lastName", is("LN")))
                .andExpect(jsonPath("$.content[0].email", is("test2@ipc.com")))
                .andExpect(jsonPath("$.content[0].status", is("REGISTERED")))
                .andExpect(jsonPath("$.content[0].siteId", is("site id2")))
                .andExpect(jsonPath("$.content[0].role.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].role.id", is("2")))
                .andExpect(jsonPath("$.content[0].role.name", is("Manufacturing")))
                .andExpect(jsonPath("$.content[0].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].company.id", is("1001")))
                .andExpect(jsonPath("$.content[0].company.name", is("IPC-TEST")))

                .andExpect(jsonPath("$.content[1].*", hasSize(8)))
                .andExpect(jsonPath("$.content[1].id", is("3")))
                .andExpect(jsonPath("$.content[1].firstName", is("FN2")))
                .andExpect(jsonPath("$.content[1].lastName", is("LN")))
                .andExpect(jsonPath("$.content[1].email", is("test@gmail.com")))
                .andExpect(jsonPath("$.content[1].status", is("REGISTERED")))
                .andExpect(jsonPath("$.content[1].siteId", is("site id")))
                .andExpect(jsonPath("$.content[1].role.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].role.id", is("1")))
                .andExpect(jsonPath("$.content[1].role.name", is("IPC Operations")))
                .andExpect(jsonPath("$.content[1].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].company.id", is("1000")))
                .andExpect(jsonPath("$.content[1].company.name", is("some company")));
    }

    @Test
    public void return422InvalidRequestForWrongSortParam() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(USER_API_END_POINT).param("page", "0").param("size", "1")
                        .param("sortParam", "wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(
                        "$.fieldErrors[?(@.resource == 'userPaginationRequestParameters' && @.code == 'Pattern' && @.field == 'sortParam')]")
                                .exists());
    }

    @Test
    public void return422InvalidRequestForWrongSortDirection() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(USER_API_END_POINT).param("page", "0").param("size", "1")
                        .param("sortDirection", "wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(
                        "$.fieldErrors[?(@.resource == 'userPaginationRequestParameters' && @.code == 'Pattern' && @.field == 'sortDirection')]")
                                .exists());
    }

    private ResultActions createUser(User userModel) throws Exception
    {
        return mockMvc.perform(MockMvcRequestBuilders.post(USER_API_END_POINT)
                .content(objectMapper.writeValueAsString(userModel))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
    }
}
