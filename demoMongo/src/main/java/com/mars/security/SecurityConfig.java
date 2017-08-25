

package com.mars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/index.html", "/forgotPass", 
                "/app/**", "/api/login/sendEmail","/api/login/forgotPass");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
       
        http
        .headers().disable()
        .csrf().disable()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
        .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/login/authenticate")
            .permitAll()
            .antMatchers("/api/**")
            .permitAll()
            .anyRequest().authenticated()
            .and()
        .exceptionHandling()
            .authenticationEntryPoint(new LoginAuthenticationEntryPoint("/login"))
            .and()
            .addFilterBefore(
                    new LoginFilter("/api/login/authenticate", userDetailsService,
                            authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception
    {
    	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
