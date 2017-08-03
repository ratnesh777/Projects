package com.ipc.userpoc.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "employeepoctest")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeePOCTest {

	/*@Id
	@GeneratedValue(strategy = IDENTITY)
	Integer id;
	
	@NotEmpty
    @Length(max = 10,message="name can not extend beyond 10 characters")
	@Column(name="name", nullable=false)
	String name;
	
	@NotEmpty
	@Pattern(regexp = "^[M|F|m|f]")
	String sex;
	*/
	
}
