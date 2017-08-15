package com.mars.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mars.exception.PortalException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.models.UserStatus;
import com.mars.mongo.repository.CustomerRepository;
import com.mars.mongo.repository.RoleRepository;
import com.mars.mongo.repository.UserRepository;
import com.mars.mongo.repository.entity.Customer;
import com.mars.mongo.repository.entity.Role;
import com.mars.mongo.repository.entity.User;
import com.mars.util.ErrorMessagesConstant;


@Service
public class UserServiceImpl extends AbstractServiceImpl implements UserService
{

    Logger auditLog = LogManager.getLogger("auditLogger");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CustomerService companyService;

    
    @Autowired
    CustomerRepository companyRepository;
    
    @Autowired
    RoleRepository roleRepository;
    

    @Override
    public User findById(String id)
    {
        User user = null;
        try
        {
            user = userRepository.findOne(id);
            auditLog.info("User found  " + user.getId() + " with metadata");

        }
        catch (Exception ignored)
        {

        }

        if (user == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.USER_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);
        }
        return hidePassword(user);
    }

    @Override
    public Page<User> findAll(User user, Integer page, Integer size, String sortParam, String sortDirection,String searchString)
    {
    	if (StringUtils.isNotBlank(searchString))
        {
            return hidePassword(searchAll(page, size, sortParam, sortDirection, searchString, user));
        }
    	
    	return user == null
                ?hidePassword(
                        userRepository.findAll(getPageRequest(page, size, sortParam, sortDirection)))
                :hidePassword(
                        userRepository.findAll(Example.of(user),getPageRequest(page, size, sortParam, sortDirection)));
    }

    @Override
    public User create(User user)
    {
        user.setId(null);
        user.setRole(roleService.findById(user.getRole() != null ? user.getRole().getId() : null));
        if (user.getRole().getId().equals("3") || user.getRole().getId().equals("4") )
        {
            validateComapnyAndProductAndSiteId(user);
            Customer customer = companyService.findCustomerById(user.getCompany() != null ? user.getCompany().getId() : null);
			Customer company= new Customer();
			company.setId(customer.getId());
			company.setName(customer.getName());
			user.setCompany(company);
        } else if (user.getRole().getId().equals("1") || user.getRole().getId().equals("2")){
            removeProductsAndCustomer(user);
        }

        
        // validate unique email value
        User userRepo = userRepository.findByEmail(user.getEmail());
        if (userRepo != null && (!userRepo.getId().equals(user.getId())))
        {
            throw new PortalServiceParameterException(ErrorMessagesConstant.DUPLICATE_USER_EMAIL)
                    .addContextValue("email", user.getEmail());
        }
       
        User saveduser = userRepository.save(user);
            auditLog.info(
                    "User successfully created  a user " + saveduser.getId() + " with metadata");
            return hidePassword(saveduser);
    }

   
    private User hidePassword(User user)
    {
        if (user != null)
        {
            user.setPassword(null);
        }
        return user;
    }

    private Page<User> hidePassword(Page<User> userPage)
    {
        for (User user : userPage)
        {
            hidePassword(user);
        }
        return userPage;
    }

    @Override
    public User updateUser(User user, boolean updateStatusForEmailInvite)
    {
       // boolean customerAdminUserFlag = roleValidationService.validateCustomAdminRole();
         
        User dbUser = userRepository.findOne(user.getId());
        if (dbUser == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.USER_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, user.getId());
        }
        // validate unique email value
        User userRepo = userRepository.findByEmail(user.getEmail());
        if (userRepo != null && (!userRepo.getId().equals(user.getId())))
        {
            throw new PortalServiceParameterException(ErrorMessagesConstant.DUPLICATE_USER_EMAIL)
                    .addContextValue("email", user.getEmail());
        }

        
       
        if (user.getProductName() != null)
        {
            dbUser.setProductName(user.getProductName());
        }
        
    
        if (dbUser.getRole().getId().equals("1") || dbUser.getRole().getId().equals("2")){
            removeProductsAndCustomer(dbUser);
        }
        
        dbUser = hidePassword(userRepository.save(dbUser));
        auditLog.info("User successfully edited a user " + dbUser.getId() + " with metadata");
        return dbUser;
    }

    private void validateComapnyAndProductAndSiteId(User user)
    {
        if (null == user.getCompany() || null == user.getProductName())// || user.getProducts().isEmpty())
        {
            throw new PortalServiceParameterException(
                    ErrorMessagesConstant.CUSTOMER_PRODUCT_NOT_NULL);
        }
    }

    @Override
    public void deleteAll()
    {
        //deleteAllPingUsers();
        userRepository.deleteAll();
        auditLog.info("User successfully deleted all users ");
    }

   

    @Override
    public void delete(List<String> ids)
    {
        List<User> userList = new ArrayList<User>();
        for (String id : ids)
        {
            User user = findById(id);
           // roleValidationService.validateRoleAndAccess(user);
            userList.add(user);
        }
       
        //for (String id : ids)
        for (User user : userList)
        {
            //ping directory delete
         //   pingUserService.deletePingUser(user.getPingId());
            userRepository.delete(user.getId());
            auditLog.info("User successfully deleted user " + user.getId() + " with metadata");
        }

    }

 
    public void unblockUsers() throws PortalServiceParameterException, PortalException
    {
        List<User> lockedUser = userRepository.findByAccountLocked(true);
        List<User> userToUnblcok = new ArrayList<User>();
        for (User user : lockedUser)
        {
            user.setAccountLocked(false);
            user.setLoginCount("0");
            userToUnblcok.add(user);

        }
        userRepository.save(userToUnblcok);
        auditLog.info("Unblocked all blocked users successfully");

    }

    public Page<User> searchAll(Integer page, Integer size, String sortParam,
            String sortDirection, String searchString, User user)
    {
    	return null;
   /*     auditLog.info("Returned paginated and sorted user data by searchString");

        QUser qUser=QUser.user;
        if(user.getCompany() != null) {
        	auditLog.info("User is not NULL. User Info : " +user);
        	Predicate predicate = (qUser.firstName.containsIgnoreCase(searchString)
        			.or(qUser.lastName.containsIgnoreCase(searchString))
        			.or(qUser.email.containsIgnoreCase(searchString))
        			.or (qUser.role.name.containsIgnoreCase(searchString))
        			.or(qUser.company.name.containsIgnoreCase(searchString))
        			.or(qUser.products.any().name.containsIgnoreCase(searchString))
        			.or(qUser.status.containsIgnoreCase(searchString))
        			.and (qUser.company.name.equalsIgnoreCase(user.getCompany().getName())));
            return addCustomerAndRoleDetails(userRepository.findAll(predicate,getPageRequest(page, size, sortParam, sortDirection)));
        }
        else
        {
        	Predicate predicate = qUser.firstName.containsIgnoreCase(searchString)
        			.or(qUser.lastName.containsIgnoreCase(searchString))
        			.or(qUser.email.containsIgnoreCase(searchString))
        			.or (qUser.role.name.containsIgnoreCase(searchString))
        			.or(qUser.company.name.containsIgnoreCase(searchString))
        			.or(qUser.products.any().name.containsIgnoreCase(searchString))
        			.or(qUser.status.containsIgnoreCase(searchString));
            return addCustomerAndRoleDetails(userRepository.findAll(predicate,getPageRequest(page, size, sortParam, sortDirection)));
        }*/
    }


    private User addCustomerAndRoleDetails(User user )
    {
    	if(user.getCompany()!=null){
    		Customer customer = companyRepository.findByName(user.getCompany().getName());
    		if(customer!=null){
    			user.setCompany(customer);
    		}
    		
    	}
    	if(user.getRole()!=null){
    		Role role= roleRepository.findByName(user.getRole().getName());
    		if(role!=null){
    			user.setRole(role);
    		}
    		
    	}

        return user;
    }

    private Page<User> addCustomerAndRoleDetails(Page<User> all)
    {
        for (User user : all)
        {
        	addCustomerAndRoleDetails(user);
        }
        return all;
    }

    private void removeProductsAndCustomer(User user) {
      //  user.setProducts(null);
        user.setCompany(null);
    }
    
	
}
