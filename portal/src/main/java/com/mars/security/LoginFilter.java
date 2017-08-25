

package com.mars.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mars.models.User;
import com.mars.repository.entity.UserEntity;


public class LoginFilter extends AbstractAuthenticationProcessingFilter 
{
    

    private final UserDetailsService userDetailsService;
    
   // private Encryptor crypto = new Encryptor();

    protected LoginFilter(String urlMapping, 
            UserDetailsService userDetailsService, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(urlMapping,"POST"));       
        this.userDetailsService = userDetailsService;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        final UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword());
         Authentication authentication = getAuthenticationManager().authenticate(loginToken);
        
         UserEntity portalUser = (UserEntity) authentication.getPrincipal();
      /*      if(portalUser.isAccountLocked()){
             throw new LockedException("User account is locked");
         }*/
         return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authentication) throws IOException, ServletException {
           
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            UserEntity user = (UserEntity) authentication.getPrincipal();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            Map<String,Object> userInfo = new HashMap<String,Object>();
            userInfo.put("email", user.getEmail());
            userInfo.put("role", user.getRole().getId());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            writer.write(new ObjectMapper().writeValueAsString(userInfo));
            
     }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
                    throws IOException, ServletException
    {
        if (exception instanceof LockedException) {
            response.setStatus(423);//account locked;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
