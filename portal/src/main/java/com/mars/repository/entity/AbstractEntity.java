package com.mars.repository.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Copyright (c) 2016 IPC Systems, Inc.
 * Created by Viktor Bondarenko on 12/7/2016.
 */
@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

}
