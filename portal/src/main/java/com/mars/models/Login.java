package com.mars.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava 
 */

@Data
@JsonInclude(value = Include.NON_NULL)
public class Login {
	
	@NotEmpty
	@Email
  	String emailId;
	
	@NotEmpty
	String password;
	
	String role;
	
	String firstName;
	
	String lastName;
	
	
}
