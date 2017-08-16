package com.mars.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

@Data
@Entity
@Table(name = "turret")
public class TurretEntity extends AbstractEntity {

	private String macAddress;

	@OneToOne
	@JoinColumn(name = "location_id", nullable = true)
	private BackRoomEntity location;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = true)
	private UserEntity user;
}
