package com.mars.service;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.mars.AbstractTest;
import com.mars.exception.PortalAccessForbiddenException;
import com.mars.exception.PortalAuthenticationException;
import com.mars.mapper.RoleMapperImpl;
import com.mars.models.Login;
import com.mars.repository.UserRepository;
import com.mars.repository.entity.UserEntity;
import com.mars.repository.entity.UserStatus;
import com.mars.service.LoginServiceImpl;
import com.mars.util.ErrorMessagesConstant;
import com.mars.util.SendMailUtil;

public class LoginServiceTest extends AbstractTest {

	@InjectMocks
	LoginServiceImpl loginService;

	@Spy
	RoleMapperImpl roleMapper;

	@Mock
	UserRepository userRepository;
	@Mock
	private SendMailUtil sendEmailService;

	@Test
	public void shouldReturnLoginDetailWhenValidDetailsPassed() throws Exception {

		Login loginModel = getLoginModel();
		UserEntity userEntity = getEntity();
		when(userRepository.findByEmail(loginModel.getEmailId())).thenReturn(userEntity);
		assertTrue(BCrypt.checkpw(loginModel.getPassword(), userEntity.getPassword()));
		loginService.doLogin(loginModel);
		verify(userRepository).findByEmail(loginModel.getEmailId());
		verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void shouldThrowAuthenticationExceptionWhenNoUserFound() throws Exception {
		thrown.expect(PortalAuthenticationException.class);
		thrown.expectMessage(containsString(ErrorMessagesConstant.INVALID_CREDENTIALS));
		Login loginModel = getLoginModel();
		when(userRepository.findByEmail(loginModel.getEmailId())).thenReturn(null);
		loginService.doLogin(loginModel);
	}
	
	@Test
	public void shouldThrowAuthenticationExceptionWhenWrongPasswordPassed() throws Exception {
		thrown.expect(PortalAuthenticationException.class);
		thrown.expectMessage(containsString(ErrorMessagesConstant.INVALID_CREDENTIALS));
		Login loginModel = getLoginModel();
		UserEntity userEntity = getEntity();
		userEntity.setPassword("$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO11");
		when(userRepository.findByEmail(loginModel.getEmailId())).thenReturn(userEntity);
		loginService.doLogin(loginModel);
	}

	@Test
	public void shouldThrowForbiddenExceptionWhenUserStatusPassedAsNotRegistered() throws Exception {
		thrown.expect(PortalAccessForbiddenException.class);
		thrown.expectMessage(containsString(ErrorMessagesConstant.ACCESS_FORBIDDEN));
		Login loginModel = getLoginModel();
		loginModel.setEmailId("test@mars.com");
		UserEntity userEntity = getEntity();
		userEntity.setStatus(UserStatus.CREATED);
		when(userRepository.findByEmail(loginModel.getEmailId())).thenReturn(userEntity);
		loginService.doLogin(loginModel);
	}
	
	@Test
	 public void sendEmailTest() throws Exception
	    {
	     UserEntity user = new UserEntity();;
	     user.setEmail("abc@gmail.com");
	     user.setPassword("test");
	     user.setPasswordToken("1112211");
	        String url = "http://localhost:8080/portal/#/resetPass?userId=1112211";
	        String mailContent = "Please follow the below link to reset your password" + "\n" + url;

	        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(user);
	        when(userRepository.save(user)).thenReturn(user);
	        loginService.sendEmail("abc@gmail.com", "http://localhost:8080");
	    }
	@Test
	public void generateToken() throws Exception
	{
		UserEntity user = new UserEntity();;
	     user.setEmail("abc@gmail.com");
	     user.setPasswordToken("1112211");
	     when(userRepository.findByEmail("abc@gmail.com")).thenReturn(user);
	     when(userRepository.save(user)).thenReturn(user);
	     loginService.generateToken("abc@gmail.com");
	    
	}
}
