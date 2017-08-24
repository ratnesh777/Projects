package com.mars.service;

import static com.mars.util.ErrorMessagesConstant.INVALID_PASSWORD_TOKEN;
import static com.mars.util.ErrorMessagesConstant.PASSWORD_FORMAT_INVALID;
import static com.mars.util.ErrorMessagesConstant.PASSWORD_LENGTH_NOT_ENOUGH;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.mars.exception.PortalAuthenticationException;
import com.mars.exception.PortalException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.models.Login;
import com.mars.mongo.repository.UserRepository;
import com.mars.mongo.repository.entity.User;
import com.mars.util.ErrorMessagesConstant;


@Service
public class LoginServiceImpl implements LoginService
{

    private static final java.lang.String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\w+\\s+@./#&/!$%^{}<>?,:;=*-~`+\\d/\\']{8,}$";

    Logger auditLog = LogManager.getLogger("auditLogger");
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Value("${base.path.uri}")
    private String BASE_PATH_URI;

    @Value("${password.token.expiration.hours}")
    private int expirationTimeOutInHours;

    @Override
    public Login doLogin(Login login)
    {
        validateEmailAndPassword(login);
        return login;
    }

    private Login validateEmailAndPassword(Login login)
    {

        User userEntity = userRepository.findByEmail(login.getEmail());

        // check whether emailId exists or not and also check the password
        if (userEntity == null || !BCrypt.checkpw(login.getPassword(), userEntity.getPassword()))
        {
            throw new PortalAuthenticationException(ErrorMessagesConstant.INVALID_CREDENTIALS);
        }

        login.setPassword(null);
        login.setRole(userEntity.getRole().getId().toString());
        login.setFirstName(userEntity.getFirstName());
        login.setLastName(userEntity.getLastName());
        return login;
    }

    public void sendEmail(String userName, String contextPathUrl)
            throws PortalAuthenticationException, PortalServiceParameterException, PortalException
    {
        try
        {
            User user = userRepository.findByEmail(userName);

            if (user != null)
            {

                String token = generateToken(userName);
                String url = contextPathUrl + "/portal/#/resetPass?userId=" + token;
                String mailContent = "Please follow the below link to reset your password" + "\n"
                        + url;
              //  sendMailService.sendPasswdResetMail(user.getEmail(), mailContent);
                auditLog.info("Email send successfully to user " + user.getEmail());

            }
            else
            {
                throw new ResourceNotFoundException("User not registered ");
            }

        }
        catch (PortalServiceParameterException ex)
        {
            throw new PortalServiceParameterException("send mail Exception " + ex);
        }
        catch (Exception ex)
        {
            throw new ResourceNotFoundException("User not registered ");
        }

    }

   

    public User findUserById(String id)
    {

        User user = userRepository.findOne(id);
        if (user == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.USER_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);
        }
        auditLog.info("Returning user data by Id: " + id);
        return user;
    }


  
 
    void validateUserForPasswordReset(User user)
    {
        if (StringUtils.isBlank(user.getPasswordToken()))
        {
            throw new PortalServiceParameterException(INVALID_PASSWORD_TOKEN);
        }
        if (StringUtils.isBlank(user.getPassword()) || user.getPassword().length() < 8)
        {
            throw new PortalServiceParameterException(PASSWORD_LENGTH_NOT_ENOUGH);
        }
        if (!Pattern.compile(PASSWORD_PATTERN).matcher(user.getPassword()).matches())
        {
            throw new PortalServiceParameterException(PASSWORD_FORMAT_INVALID);
        }
    }

    

    public String generateToken(String username)
            throws PortalServiceParameterException, PortalException
    {
        try
        {
            User user = userRepository.findByEmail(username);
            String token = UUID.randomUUID().toString();
            user.setPasswordToken(token);
            user.setPasswordTokenExpirationDate(
                    DateUtils.addHours(new Date(), expirationTimeOutInHours));
            userRepository.save(user);
            auditLog.info("Token  updated successfully to user " + user.getId());

            return token;
        }
        catch (PortalServiceParameterException exception)
        {
            throw new PortalServiceParameterException("Token Generation Failed " + exception);
        }
        catch (Exception exception)
        {
            throw new PortalException("Token Generation Failed", exception)
                    .addContextValue("User Name ", username);
        }

    }



}
