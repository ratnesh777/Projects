package com.mars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.RoleMapper;
import com.mars.models.Role;
import com.mars.repository.RoleEntityRepository;
import com.mars.repository.entity.RoleEntity;
import com.mars.util.ErrorMessagesConstant;

@Service
public class RoleServiceImpl extends AbstractServiceImpl implements RoleService
{
    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role findById(String id)
    {
        RoleEntity entity = null;
        try
        {
            entity = roleEntityRepository.findOne(Integer.parseInt(id));
        }
        catch (Exception ignored)
        {
        }
        if (entity == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.ROLE_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);

        }
        return roleMapper.toModel(entity);
    }

    @Override
    public Page<Role> findAll(Integer page, Integer size, String sortParam, String sortDirection)
    {
        return roleEntityRepository.findAll(getPageRequest(page, size, sortParam, sortDirection))
                .map(roleEntity -> roleMapper.toModel(roleEntity));
    }
}
