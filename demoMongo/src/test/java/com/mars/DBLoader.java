package com.mars;

import static java.util.Arrays.asList;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mars.mongo.repository.CustomerRepository;
import com.mars.mongo.repository.RoleRepository;
import com.mars.mongo.repository.UserRepository;
import com.mars.mongo.repository.entity.Customer;
import com.mars.mongo.repository.entity.Role;
import com.mars.mongo.repository.entity.User;

public class DBLoader {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CustomerRepository companyRepository;

	@Autowired
	UserRepository userRepository;

	@PostConstruct
	public void load() {
		addRoles();
		addCustomers();
		addUsers();
	}

	public void addUsers() {
		userRepository.save(

				createUser("3", "test@gmail.com", "FN2", "LN", "1000", "1",
						"$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO",
						"f74780df-6af7-468a-a3a2-53a8d5521cc7", DateUtils.addHours(new Date(), 24)));
	}

	public void addCustomers() {
		companyRepository.save(asList(createCustomer("1000", "some company"), createCustomer("1001", "TEST")));
	}

	private void addRoles() {
		roleRepository.save(asList(createRole("1", "Admin User"), createRole("4", "Customer User")));
	}

	private User createUser(String id, String email, String firstName, String lastName, String customerId,
			String roleId, String password, String token, Date tokenExpirationDate) {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setCompany(createCustomer(customerId, "test company"));
		user.setRole(createRole(roleId, "Admin User"));
		user.setPasswordToken(token);
		user.setPasswordTokenExpirationDate(tokenExpirationDate);
		return user;
	}

	private Customer createCustomer(String id, String name) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);
		return customer;
	}

	private Role createRole(String id, String name) {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		return role;
	}

}
