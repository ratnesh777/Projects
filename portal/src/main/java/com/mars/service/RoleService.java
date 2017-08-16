package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.models.Role;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Viktor Bondarenko on 12/16/2016.
 */
public interface RoleService
{
    Role findById(String id);

    Page<Role> findAll(Integer page, Integer size, String sortParam, String sortDirection);
}
