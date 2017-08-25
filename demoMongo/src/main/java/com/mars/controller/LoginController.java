package com.mars.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mars.error.ErrorResource;
import com.mars.exception.InvalidRequestException;
import com.mars.models.Login;
import com.mars.mongo.repository.entity.User;
import com.mars.security.UserDetailsService;
import com.mars.service.LoginService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = APIUtilConstant.LOGIN_API_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = APIUtilConstant.SWAGGER_LOGIN_API)
public class LoginController
{

    @Value("${base.path.uri}")
    private String BASE_PATH_URI;

    @Autowired
    public LoginService loginService;
    
    @Autowired
    UserDetailsService userDetailsService;

    @ApiOperation(value = "Users authentication by emailId and password", response = Login.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Login.class),
            @ApiResponse(code = 422, message = "INVALID_REQUEST", response = ErrorResource.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResource.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.POST, path = "/authenticate", produces = "application/json", consumes = "application/json")
    public Login doLogin(@RequestBody @Valid Login login, BindingResult bindingResult)
    {

        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException("Invalid create request", bindingResult);
        }
        return loginService.doLogin(login);

    }

    /**
     * Returns authentication result for requested user
     * 
     * @param user user model
     * @return <code>User</code> json representation
     */
    @ApiOperation(value = "returns Account of currently logged in user", response = Login.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.OK, response = Login.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/security/account", produces = "application/json")
    public @ResponseBody Map<String, Object> loggedInAccount()
    {
    	 //User user = null;
         User user = (User) userDetailsService.loadUserByUsername(SecurityUtils.getCurrentLogin());
        Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("email", user.getUsername());
        userInfo.put("role", user.getRole().getId());
        userInfo.put("firstName", user.getFirstName());
        userInfo.put("lastName", user.getLastName());
        if(user.getCompany()!= null){
            userInfo.put("customerId", user.getCompany().getId());
        }
        return userInfo;
    }

    
}
