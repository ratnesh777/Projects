package com.mars.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "product")
public class ProductEntity extends AbstractEntity {

	@NotNull
	private String name;
}
