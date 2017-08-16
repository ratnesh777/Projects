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
import com.mars.models.User;
import com.mars.models.UserPaginationRequestParameters;
import com.mars.service.UserService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = APIUtilConstant.USER_API_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "User APIs Detail")
public class UserController
{

    @Value("${base.path.uri}")
    private String BASE_PATH_URI;

    @Autowired
    public UserService userService;

    @ApiOperation(value = "Creates user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ErrorMessagesConstant.CREATED, response = User.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user,
            BindingResult bindingResult)
    {

        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException("Invalid create request", bindingResult);
        }
        user = userService.create(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",
                BASE_PATH_URI + APIUtilConstant.USER_API_END_POINT + "/" + user.getId());
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get user  by Id", response = User.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = User.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(
            @ApiParam(name = "id", value = "user_id", required = true) @PathVariable("id") String id)
            throws ResourceNotFoundException {
        return userService.findById(id);
    }

    @ApiOperation(value = "Find all users by pages", responseContainer = "List", response = User.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = User.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class) })

    @RequestMapping(method = RequestMethod.GET)
    public Page<User> getAll(
            @Valid @ModelAttribute() UserPaginationRequestParameters paginationRequestParameters,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED, bindingResult);
        }

        return userService.findAll(paginationRequestParameters.getPage(),
                paginationRequestParameters.getSize(), paginationRequestParameters.getSortParam(),
                paginationRequestParameters.getSortDirection());

    }
}
