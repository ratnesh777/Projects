package com.mars.mapper;

import com.mars.AbstractSpringTest;
import com.mars.mapper.RoleMapperImpl;
import com.mars.models.Role;
import com.mars.repository.entity.RoleEntity;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RoleMapperImplTest extends AbstractSpringTest{

    @Autowired
    RoleMapperImpl roleMapper;

    @Test
    public void convertNotNullUserModelToNotNullUserEntity() throws Exception {
        Role model = new Role();
        model.setId("1");
        model.setName("role");

        RoleEntity entity = new RoleEntity();
        entity.setId(1);
        entity.setName("role");
        assertThat(roleMapper.toEntity(model), new ReflectionEquals(entity));
    }

    @Test
    public void convertNullUserModelToNullUserEntity() throws Exception {
        assertNull(roleMapper.toEntity(null));
    }

    @Test
    public void convertNotNullUserEntityToNotNullUserModel() throws Exception {
        RoleEntity entity = new RoleEntity();
        entity.setId(1);
        entity.setName("role");

        Role model = new Role();
        model.setId("1");
        model.setName("role");
        assertThat(roleMapper.toModel(entity), new ReflectionEquals(model));
    }

    @Test
    public void convertNullUserEntityToNullUserModel() throws Exception {
        assertNull(roleMapper.toModel(null));
    }
}