package com.ipc.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
//@Entity
//@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

	//@Id
	//@GeneratedValue(strategy = IDENTITY)
	String id;
	
	@NotEmpty
    @Length(max = 10,message="name can not extend beyond 10 characters")
	String name;
	
	@NotEmpty
	@Pattern(regexp = "^[M|F|m|f]")
	String sex;
	
	//@Valid
	//List<Address> addresses;
	
	
}
