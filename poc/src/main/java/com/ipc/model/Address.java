package com.ipc.model;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
	
	@NotBlank
	String address;
	
	@NotBlank
	String zip;

}
