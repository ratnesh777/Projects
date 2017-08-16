package com.mars.controller;

import static com.mars.util.APIUtilConstant.BACK_ROOM_API_END_POINT;
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
import com.mars.models.BackRoom;
import com.mars.util.ErrorMessagesConstant;

public class BackRoomControllerTest extends AbstractControllerTest
{

    @Test
    public void createBackRoom() throws Exception
    {
        BackRoom backRoomModel = getBackRoomModel();
        backRoomModel.setName("London");

        ResultActions resultActions = createBackRoom(backRoomModel);
        resultActions.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("London")))
                .andExpect(jsonPath("$.homeZoneIP", is("11.11.11.11")))
                .andExpect(jsonPath("$.managementProxyIP", is("12.22.22.22")))
                .andExpect(jsonPath("$.voipProxyIP", is("13.33.33.33")))
                .andExpect(jsonPath("$.company.*", hasSize(2)))
                .andExpect(jsonPath("$.company.id", is("1001")))
                .andExpect(jsonPath("$.company.name", is("IPC-TEST")));

        MockHttpServletResponse response = resultActions.andReturn().getResponse();
        String id = objectMapper.readValue(response.getContentAsByteArray(), BackRoom.class)
                .getId();
        assertEquals("http://localhost:8080/portal" + BACK_ROOM_API_END_POINT + "/" + id,
                response.getRedirectedUrl());
    }

    @Test
    public void createBackRoomFailedIfRequiredFieldsAreEmpty() throws Exception
    {
        BackRoom backRoom = getBackRoomModel();
        backRoom.setName(" ");
        backRoom.setCompany(null);

        ResultActions resultActions = createBackRoom(backRoom);
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Invalid create request")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath(getNotBlankErrorExpression("backRoom", "name")).exists())
                .andExpect(jsonPath(getNotNullErrorExpression("backRoom", "company")).exists());
    }

    @Test
    public void createBackRoomFailedIfRequiredFieldsAreNull() throws Exception
    {
        BackRoom backRoom = getBackRoomModel();
        backRoom.setName(null);
        backRoom.setCompany(null);

        ResultActions resultActions = createBackRoom(backRoom);
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Invalid create request")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath(getNotBlankErrorExpression("backRoom", "name")).exists())
                .andExpect(jsonPath(getNotNullErrorExpression("backRoom", "company")).exists());
    }

    @Test
    public void return422ForWrongIPs() throws Exception
    {
        BackRoom backRoomModel = getBackRoomModel();
        backRoomModel.setHomeZoneIP("10.205.232");
        backRoomModel.setManagementProxyIP("10.205.232.a");
        backRoomModel.setVoipProxyIP("10.205.232.355");

        ResultActions resultActions = createBackRoom(backRoomModel);
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Invalid create request")))
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
                .andExpect(jsonPath(getNotValidIpAddressErrorExpression("backRoom", "homeZoneIP"))
                        .exists())
                .andExpect(jsonPath(
                        getNotValidIpAddressErrorExpression("backRoom", "managementProxyIP"))
                                .exists())
                .andExpect(jsonPath(getNotValidIpAddressErrorExpression("backRoom", "voipProxyIP"))
                        .exists());
    }

    @Test
    public void return404ForNotExistingCompany() throws Exception
    {
        BackRoom backRoom = getBackRoomModel();
        backRoom.getCompany().setId("1");

        ResultActions resultActions = createBackRoom(backRoom);
        resultActions.andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is("Invalid Request")))
                .andExpect(jsonPath("$.message", is("Could not find company by id")));
    }

    @Test
    public void return422ForDuplicatedNameWithingCompany() throws Exception
    {
        ResultActions resultActions = createBackRoom(getBackRoomModel());
        resultActions.andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is("Parameters passed in the request is invalid.")))
                .andExpect(jsonPath("$.message", is("Back room name already exist in the company")))
                .andExpect(jsonPath("$.errorContext", hasSize(2)))
                .andExpect(jsonPath(getContextItem("back room name", "back room 1")).exists())
                .andExpect(jsonPath(getContextItem("company", "Company(id=1001, name=IPC-TEST)"))
                        .exists());
    }

    @Test
    public void findById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(6))).andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("back room 1")))
                .andExpect(jsonPath("$.homeZoneIP", is("11.11.11.11")))
                .andExpect(jsonPath("$.voipProxyIP", is("13.33.33.33")))
                .andExpect(jsonPath("$.managementProxyIP", is("12.22.22.22")))
                .andExpect(jsonPath("$.company.*", hasSize(2)))
                .andExpect(jsonPath("$.company.id", is("1001")))
                .andExpect(jsonPath("$.company.name", is("IPC-TEST")));
    }

    @Test
    public void return404ForNotExistingBackRoomID() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT + "/555")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message",
                        containsString(ErrorMessagesConstant.BACK_ROOM_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.message", containsString("id=555")));
    }

    @Test
    public void return404ForNonNumericId() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT + "/abc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(jsonPath("$.message",
                        containsString(ErrorMessagesConstant.BACK_ROOM_ID_NOT_FOUND)))
                .andExpect(jsonPath("$.message", containsString("id=abc")));
    }

    public void returnFirstPageOfBackRooms() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT).param("page", "0")
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
    public void returnAllBackRooms() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT)
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
    public void returnAscSortedBackRooms() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT).param("page", "0")
                .param("size", "2").param("sortParam", "name").param("sortDirection", "asc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(6)))
                .andExpect(jsonPath("$.content[0].id", is("1")))
                .andExpect(jsonPath("$.content[0].name", is("back room 1")))
                .andExpect(jsonPath("$.content[0].homeZoneIP", is("11.11.11.11")))
                .andExpect(jsonPath("$.content[0].managementProxyIP", is("12.22.22.22")))
                .andExpect(jsonPath("$.content[0].voipProxyIP", is("13.33.33.33")))
                .andExpect(jsonPath("$.content[0].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].company.id", is("1001")))
                .andExpect(jsonPath("$.content[0].company.name", is("IPC-TEST")))

                .andExpect(jsonPath("$.content[1].*", hasSize(6)))
                .andExpect(jsonPath("$.content[1].id", is("2")))
                .andExpect(jsonPath("$.content[1].name", is("back room 2")))
                .andExpect(jsonPath("$.content[1].homeZoneIP", is("21.11.11.11")))
                .andExpect(jsonPath("$.content[1].managementProxyIP", is("22.22.22.22")))
                .andExpect(jsonPath("$.content[1].voipProxyIP", is("23.33.33.33")))
                .andExpect(jsonPath("$.content[1].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].company.id", is("1000")))
                .andExpect(jsonPath("$.content[1].company.name", is("some company")));
    }

    @Test
    public void returnAscSortedBackRoomsIfSortDirectionIsNull() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT).param("page", "0").param("size", "2")
                        .param("sortParam", "name").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(6)))
                .andExpect(jsonPath("$.content[0].id", is("1")))
                .andExpect(jsonPath("$.content[0].name", is("back room 1")))
                .andExpect(jsonPath("$.content[0].homeZoneIP", is("11.11.11.11")))
                .andExpect(jsonPath("$.content[0].managementProxyIP", is("12.22.22.22")))
                .andExpect(jsonPath("$.content[0].voipProxyIP", is("13.33.33.33")))
                .andExpect(jsonPath("$.content[0].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].company.id", is("1001")))
                .andExpect(jsonPath("$.content[0].company.name", is("IPC-TEST")))

                .andExpect(jsonPath("$.content[1].*", hasSize(6)))
                .andExpect(jsonPath("$.content[1].id", is("2")))
                .andExpect(jsonPath("$.content[1].name", is("back room 2")))
                .andExpect(jsonPath("$.content[1].homeZoneIP", is("21.11.11.11")))
                .andExpect(jsonPath("$.content[1].managementProxyIP", is("22.22.22.22")))
                .andExpect(jsonPath("$.content[1].voipProxyIP", is("23.33.33.33")))
                .andExpect(jsonPath("$.content[1].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].company.id", is("1000")))
                .andExpect(jsonPath("$.content[1].company.name", is("some company")));
    }

    @Test
    public void returnDescSortedBackRooms() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT).param("page", "0")
                .param("size", "2").param("sortParam", "voipProxyIP").param("sortDirection", "desc")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(9))).andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.last", is(false))).andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.sort", is(notNullValue())))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].*", hasSize(6)))
                .andExpect(jsonPath("$.content[0].id", is("3")))
                .andExpect(jsonPath("$.content[0].name", is("back room 3")))
                .andExpect(jsonPath("$.content[0].homeZoneIP", is("31.11.11.11")))
                .andExpect(jsonPath("$.content[0].managementProxyIP", is("32.22.22.22")))
                .andExpect(jsonPath("$.content[0].voipProxyIP", is("33.33.33.33")))
                .andExpect(jsonPath("$.content[0].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[0].company.id", is("1001")))
                .andExpect(jsonPath("$.content[0].company.name", is("IPC-TEST")))

                .andExpect(jsonPath("$.content[1].*", hasSize(6)))
                .andExpect(jsonPath("$.content[1].id", is("2")))
                .andExpect(jsonPath("$.content[1].name", is("back room 2")))
                .andExpect(jsonPath("$.content[1].homeZoneIP", is("21.11.11.11")))
                .andExpect(jsonPath("$.content[1].managementProxyIP", is("22.22.22.22")))
                .andExpect(jsonPath("$.content[1].voipProxyIP", is("23.33.33.33")))
                .andExpect(jsonPath("$.content[1].company.*", hasSize(2)))
                .andExpect(jsonPath("$.content[1].company.id", is("1000")))
                .andExpect(jsonPath("$.content[1].company.name", is("some company")));
    }

    @Test
    public void return422InvalidRequestForWrongSortParam() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT).param("page", "0").param("size", "1")
                        .param("sortParam", "wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(
                        "$.fieldErrors[?(@.resource == 'backRoomPaginationRequestParameters' && @.code == 'Pattern' && @.field == 'sortParam')]")
                        .exists());
    }

    @Test
    public void return422InvalidRequestForWrongSortDirection() throws Exception
    {
        mockMvc.perform(
                MockMvcRequestBuilders.get(BACK_ROOM_API_END_POINT).param("page", "0").param("size", "1")
                        .param("sortDirection", "wrong").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.code", is(ErrorMessagesConstant.INVALID_REQUEST)))
                .andExpect(
                        jsonPath("$.message", is(ErrorMessagesConstant.INVALID_PARAMETER_PASSED)))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath(
                        "$.fieldErrors[?(@.resource == 'backRoomPaginationRequestParameters' && @.code == 'Pattern' && @.field == 'sortDirection')]")
                        .exists());
    }

    private ResultActions createBackRoom(BackRoom backRoom) throws Exception
    {
        return mockMvc.perform(MockMvcRequestBuilders.post(BACK_ROOM_API_END_POINT)
                .content(objectMapper.writeValueAsString(backRoom))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
    }
}
