package com.mars.controller;

import javax.validation.Valid;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.mars.models.BackRoom;
import com.mars.models.BackRoomPaginationRequestParameters;
import com.mars.service.BackRoomService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = APIUtilConstant.BACK_ROOM_API_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = APIUtilConstant.SWAGGER_BACK_ROOM_API)
public class BackRoomController
{
    @Value("${base.path.uri}")
    private String BASE_PATH_URI;
    @Qualifier("backRoomServiceImpl")
    @Autowired
    private BackRoomService backRoomService;

    @ApiOperation(value = "Creates backRoom", response = BackRoom.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ErrorMessagesConstant.CREATED, response = BackRoom.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<BackRoom> create(@RequestBody @Valid BackRoom backRoom,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_CREATE_REQUEST,
                    bindingResult);
        }
        backRoom = backRoomService.create(backRoom);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",
                BASE_PATH_URI + APIUtilConstant.BACK_ROOM_API_END_POINT + "/" + backRoom.getId());
        return new ResponseEntity<>(backRoom, httpHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get back room  by Id", response = BackRoom.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = BackRoom.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BackRoom getById(
            @ApiParam(name = "id", value = "back_room_id", required = true) @PathVariable("id") String id)
            throws ResourceNotFoundException {
        return backRoomService.findById(id);
    }

    @ApiOperation(value = "Find all back rooms by pages", responseContainer = "List", response = BackRoom.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = BackRoom.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class) })

    @RequestMapping(method = RequestMethod.GET)
    public Page<BackRoom> getAll(
            @Valid @ModelAttribute() BackRoomPaginationRequestParameters paginationRequestParameters,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED, bindingResult);
        }

        return backRoomService.findAll(paginationRequestParameters.getPage(),
                paginationRequestParameters.getSize(), paginationRequestParameters.getSortParam(),
                paginationRequestParameters.getSortDirection());

    }
}
