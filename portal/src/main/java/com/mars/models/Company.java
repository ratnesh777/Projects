package com.mars.models;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */


@JsonInclude(value = Include.NON_NULL)
@Data
public class Company {

	String id;
	
	@NotEmpty
	String name;
}
