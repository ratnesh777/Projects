package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.mongo.repository.entity.Role;

public interface RoleService
{
    Role findById(String id);

    Page<Role> findAll(Integer page, Integer size, String sortParam, String sortDirection);

}
