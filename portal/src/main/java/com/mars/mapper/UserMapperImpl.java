package com.mars.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mars.models.User;
import com.mars.repository.entity.UserEntity;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

@Service
public class UserMapperImpl extends AbstractBaseMapper<UserEntity, User> implements UserMapper
{
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public User toModel(UserEntity entity)
    {
        if (entity == null)
        {
            return null;
        }
        User user = new User();
        user.setId(entity.getId() != null ? entity.getId().toString() : null);
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setRole(roleMapper.toModel(entity.getRole()));
        user.setCompany(companyMapper.toModel(entity.getCompany()));
        user.setStatus(entity.getStatus());
        user.setEmail(entity.getEmail());
        user.setSiteId(entity.getSiteId());
        return user;
    }

    @Override
    public UserEntity toEntity(User model)
    {
        if (model == null)
        {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(
                StringUtils.isNumeric(model.getId()) ? Integer.parseInt(model.getId()) : null);
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setRole(roleMapper.toEntity(model.getRole()));
        entity.setCompany(companyMapper.toEntity(model.getCompany()));
        entity.setStatus(model.getStatus());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setSiteId(model.getSiteId());
        return entity;
    }
}
