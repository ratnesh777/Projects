package com.ipc.repository;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class EmpEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String name;
	String sex;
	
	//table related fields
	@Column(name = "last_modified")
	@Temporal(TemporalType.TIMESTAMP)
	Date lastModifiedDate = new Date();
	
	@Column(name = "created_by", nullable=true)
	String createdBy; 

}
