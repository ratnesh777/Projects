package com.mars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mars.exception.ResourceNotFoundException;
import com.mars.mongo.repository.RoleRepository;
import com.mars.mongo.repository.entity.Role;
import com.mars.util.ErrorMessagesConstant;




@Service
public class RoleServiceImpl extends AbstractServiceImpl implements RoleService
{
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findById(String id)
    {
        Role role = null;
        try
        {
            role = roleRepository.findOne(id);
        }
        catch (Exception ignored)
        {
        }
        if (role == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.ROLE_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);

        }
        return role;
    }

    @Override
    public Page<Role> findAll(Integer page, Integer size, String sortParam, String sortDirection)
    {
        return roleRepository.findAll(getPageRequest(page, size, sortParam, sortDirection));
    }

}
