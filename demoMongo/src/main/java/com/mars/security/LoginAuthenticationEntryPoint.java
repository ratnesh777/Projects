
package com.mars.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class LoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public LoginAuthenticationEntryPoint(String loginFormUrl)
    {
        super(loginFormUrl);        
    }

    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        if(httpServletRequest.getRequestURI().contains("api")) {
           httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
        else{
            new DefaultRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, "/login");      
        }
    	//httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
