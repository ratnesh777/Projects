package com.mars.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mars.exception.PortalException;
import com.mars.exception.PortalServiceParameterException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.CompanyMapper;
import com.mars.mapper.RoleMapper;
import com.mars.mapper.UserMapper;
import com.mars.models.User;
import com.mars.repository.UserRepository;
import com.mars.repository.entity.UserEntity;
import com.mars.repository.entity.UserStatus;
import com.mars.util.ErrorMessagesConstant;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Viktor Bondarenko on 12/15/2016.
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl implements UserService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    CompanyService companyService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public User findById(String id)
    {
        return userMapper.toModel(findUserEntity(id));
    }

    @Override
    public User findByIdWithPassword(String id)
    {
        UserEntity userEntity = findUserEntity(id);
        User user = userMapper.toModel(userEntity);
        user.setPassword(userEntity.getPassword());
        return user;
    }

    @Override
    public Page<User> findAll(Integer page, Integer size, String sortParam, String sortDirection)
    {
        return userRepository.findAll(getPageRequest(page, size, sortParam, sortDirection))
                .map(userEntity -> userMapper.toModel(userEntity));
    }

    @Override
    @Transactional
    public User create(User user)
    {
        user.setId(null);
        UserEntity entity = userMapper.toEntity(user);
        entity.setRole(roleMapper.toEntity(
                roleService.findById(user.getRole() != null ? user.getRole().getId() : null)));
        entity.setCompany(companyMapper.toEntity(companyService
                .findCompanyById(user.getCompany() != null ? user.getCompany().getId() : null)));
        entity.setStatus(UserStatus.CREATED);
        entity.setPassword(
                BCrypt.hashpw(RandomStringUtils.randomAlphanumeric(16), BCrypt.gensalt()));
        try
        {
            return userMapper.toModel(userRepository.save(entity));
        }
        catch (DataIntegrityViolationException e)
        {
            if (e.getCause() != null && e.getCause().getCause() != null
                    && StringUtils.containsIgnoreCase(e.getCause().getCause().getMessage(),
                            "email_UniqueConstraint"))
            {
                throw new PortalServiceParameterException(
                        ErrorMessagesConstant.DUPLICATE_USER_EMAIL).addContextValue("email",
                                user.getEmail());
            }
            throw new PortalException(ErrorMessagesConstant.ERROR_CREATE_USER, e);
        }
    }

    UserEntity findUserEntity(String id)
    {
        UserEntity userEntity = null;
        try
        {
            userEntity = userRepository.findOne(Integer.parseInt(id));
        }
        catch (Exception ignored)
        {

        }

        if (userEntity == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.USER_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);
        }
        return userEntity;
    }
}
