package com.mars.mongo.repository.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
@Document(collection = "customer")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String id;

	 private static final String  HTPP_URL_REGEXP="^(http|https)://.*$";
	
	
	 private static final String FQDN_REGEXP ="^((?!-)[A-Za-z0-9-]{1,254}(?<!-)\\.)+[A-Za-z]{2,6}$"; 
	 
	 private static final String WRONG_URL_ADDRESS_MESSAGE = "should be valid URL ";
	 
	 private static final String WRONG_FQDN_ADDRESS_MESSAGE = "should be valid NTR Server FQDN address";
	 
	@NotBlank
	String name;

	String address;

	@Pattern(regexp=HTPP_URL_REGEXP ,message=WRONG_URL_ADDRESS_MESSAGE)
	String websiteURL;

	String emailDomain;
	
}
