package com.mars.mapper;

import com.mars.models.Role;
import com.mars.repository.entity.RoleEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMapperImpl extends AbstractBaseMapper<RoleEntity, Role> implements RoleMapper {
    @Override
    public Role toModel(RoleEntity entity) {
        if (entity == null){
            return null;
        }
        Role model = new Role();
        model.setId(entity.getId() != null ? entity.getId().toString() : null);
        model.setName(entity.getName());
        return model;
    }

    @Override
    public RoleEntity toEntity(Role model) {
        if (model == null) {
            return null;
        }
        RoleEntity entity = new RoleEntity();
        entity.setId(StringUtils.isNumeric(model.getId()) ? Integer.valueOf(model.getId()) : null);
        entity.setName(model.getName());
        return entity;
    }

}
