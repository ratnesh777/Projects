package com.mars.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.mars.models.User;
import com.mars.service.LoginService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value=APIUtilConstant.LOGIN_API_END_POINT,produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = APIUtilConstant.SWAGGER_LOGIN_API)
public class LoginController {

	@Value("${base.path.uri}")
	private String BASE_PATH_URI;
	
	@Autowired
	public LoginService loginService;
	
    @ApiOperation(value = "Users authentication by emailId and password", response = Login.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Login.class),
            @ApiResponse(code = 422, message = "INVALID_REQUEST", response = ErrorResource.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorResource.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.POST, path = "/authenticate", produces = "application/json", consumes = "application/json")
		public Login doLogin(@RequestBody @Valid Login login, BindingResult bindingResult){

			if (bindingResult.hasErrors()) {
				throw new InvalidRequestException("Invalid create request", bindingResult);
			}
			return loginService.doLogin(login);
			
			
		}
    
    /**
     * Sends Email for Reset Password
     * 
     * @param username user name
     * @return <code>User</code> json representation
     */
    @ApiOperation(value = "Sends mail to user ", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ErrorMessagesConstant.SUCCESS, response = User.class),
            @ApiResponse(code = 200, message = ErrorMessagesConstant.OK, response = User.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response =ErrorResource.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
    @RequestMapping(method = RequestMethod.POST, path = "/sendEmail", consumes = "application/json")
    public @ResponseBody void sendEmail(@RequestBody String username, HttpServletRequest req)
    {
    	loginService.sendEmail(username, getUrl(req));
    }
    
    /**
     * Returns Constructed Url from the HttpServletRequest
     * 
     * @param httpRequest
     * @return <code>User</code> json representation
     */

    private String getUrl(HttpServletRequest httpRequest)
    {
        String scheme = httpRequest.getScheme(); // http
        String serverName = httpRequest.getServerName(); // hostname.com
        int serverPort = httpRequest.getServerPort(); // 80
        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        if (serverName == "localhost")
        {
            url.append(":").append("8080");
        }
        else
        {
            url.append(":").append(serverPort);
        }
        return url.toString();
    }
}
