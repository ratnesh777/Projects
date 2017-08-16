package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.models.User;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Viktor Bondarenko on 12/15/2016.
 */
public interface UserService
{

    User findById(String id);

    User findByIdWithPassword(String id);

    Page<User> findAll(Integer page, Integer size, String sortParam, String sortDirection);

    User create(User user);
}
