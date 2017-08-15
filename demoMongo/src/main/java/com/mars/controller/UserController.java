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
import org.springframework.scheduling.annotation.Scheduled;
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
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.models.UserPaginationRequestParameters;
import com.mars.mongo.repository.entity.User;
import com.mars.service.CustomerService;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = User.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(
            @ApiParam(name = "id", value = "user_id", required = true) @PathVariable("id") String id)
            throws ResourceNotFoundException
    {

        User user = userService.findById(id);
        return user;
    }

    @ApiOperation(value = "Find all users by pages", responseContainer = "List", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = User.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.GET)
    public Page<User> getAll(
            @Valid @ModelAttribute() UserPaginationRequestParameters paginationRequestParameters,
            BindingResult bindingResult)
    {

        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED,
                    bindingResult);
        }

        
    	User user = new User();
        return userService.findAll(user,paginationRequestParameters.getPage(),
                paginationRequestParameters.getSize(), paginationRequestParameters.getSortParam(),
                paginationRequestParameters.getSortDirection(),paginationRequestParameters.getSearchKey());

    }

	@ApiOperation(value = "Update user by Id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = User.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class) })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody @Valid User user, BindingResult bindingResult,
            @ApiParam(name = "id", value = "user id", required = true) @PathVariable("id") String id)
    {
        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED,
                    bindingResult);
        }

        user.setId(id);
        return userService.updateUser(user,false);
    }

    @ApiOperation(value = "Deletes user")
    @ApiResponses(value = { @ApiResponse(code = 204, message = ErrorMessagesConstant.NO_CONTENT),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByIds(
            @ApiParam(name = "isDeleteAll", value = "Pass true to delete all users", required = true) @RequestParam(value = "isDeleteAll", required = true) boolean isDeleteAll,
            @ApiParam(name = "ids", value = "ids of endpoint", required = false) @RequestParam(value = "ids", required = false) String[] ids)
    {
  
        if (isDeleteAll)
        {
            userService.deleteAll();
        }
        else
        {
            if (ids == null || ids.length == 0)
            {
                throw new PortalServiceParameterException(
                        ErrorMessagesConstant.USER_IDS_PASSED_NULL_WHEN_ISDELETEALL_PASSED)
                                .addContextValue(ErrorMessagesConstant.IDS, "");
            }
            else
            {
                userService.delete(Arrays.asList(ids));
            }

        }

    }
    
    //Scheduler to unblock user after every 10 minutes 
    @Scheduled(fixedDelay=600000)
    public void unLockUserScheduler(){
    }
    
    
}
