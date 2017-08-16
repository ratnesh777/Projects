package com.mars.repository.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(exclude={"backRooms", "users"})
@Entity
@Table(name="company")
public class CompanyEntity extends AbstractEntity {

	 private String name;

	@OneToMany(mappedBy = "company",fetch=FetchType.EAGER)
	private Set<BackRoomEntity> backRooms;
	
	@OneToMany(mappedBy = "company",fetch=FetchType.EAGER)
	private Set<UserEntity> users;

}
