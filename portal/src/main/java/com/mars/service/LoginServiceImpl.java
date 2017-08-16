package com.mars.service;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.mars.exception.PortalAccessForbiddenException;
import com.mars.exception.PortalAuthenticationException;
import com.mars.exception.PortalException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.mapper.RoleMapper;
import com.mars.mapper.UserMapper;
import com.mars.models.Login;
import com.mars.models.User;
import com.mars.repository.UserRepository;
import com.mars.repository.entity.UserEntity;
import com.mars.repository.entity.UserStatus;
import com.mars.util.ErrorMessagesConstant;
import com.mars.util.SendMailUtil;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Value("${base.path.uri}")
	private String BASE_PATH_URI;

	@Autowired
	private SendMailUtil sendMailService;
	
	@Override
	public Login doLogin(Login login) {
		validateEmailAndPassword(login);
		return login;
	}

	private Login validateEmailAndPassword(Login login) {
		
		UserEntity userEntity = userRepository.findByEmail(login.getEmailId());
	
		//check whether emailId exists or not and also check the password
		if(userEntity == null || !BCrypt.checkpw(login.getPassword(), userEntity.getPassword())){
			throw new PortalAuthenticationException(ErrorMessagesConstant.INVALID_CREDENTIALS);
		}
		
		//check whether user has REGISTERED status or not
		if ( !userEntity.getStatus().equals(UserStatus.REGISTERED) )
        {
            throw new PortalAccessForbiddenException(ErrorMessagesConstant.ACCESS_FORBIDDEN);
        }
	 
		login.setPassword(null);
		login.setRole(userEntity.getRole().getId().toString());
		login.setFirstName(userEntity.getFirstName());
		login.setLastName(userEntity.getLastName());
		return login;
	}
	
	/**
     * Sends Email to the User
     *
     * @param userName - userName of user whome email to be sent
     * @Return <code>String<code/> send status is returned.
     */
	public void sendEmail(String userName, String contextPathUrl)
			throws PortalAuthenticationException, PortalServiceParameterException, PortalException {
		try {
			UserEntity user = userRepository.findByEmail(userName);
			if (user != null) {

				String token = generateToken(userName);
				String url = contextPathUrl + "/portal/#/resetPass?userId=" + token;
				String mailContent = "Please follow the below link to reset your password" + "\n" + url;
				sendMailService.sendPasswdResetMail(user.getEmail(), mailContent);

			}

		} catch (PortalServiceParameterException ex) {
			throw new PortalServiceParameterException("send mail Exception " + ex);
		} catch (Exception ex) {
			throw new PortalException(ex);
		}

	}
	  
	/**
	 * Generates Password Token for Password Reset
	 *
	 * @Param userName
	 */

	public String generateToken(String username) throws PortalServiceParameterException, PortalException {
		try {
			UserEntity user = userRepository.findByEmail(username);
			String token = UUID.randomUUID().toString();
			user.setPasswordToken(token);
			userRepository.save(user);
			return token;
		} catch (PortalServiceParameterException exception) {
			throw new PortalServiceParameterException("Token Generation Failed " + exception);
		} catch (Exception exception) {
			throw new PortalException("Token Generation Failed", exception).addContextValue("User Name ", username);
		}

	}
	
}
