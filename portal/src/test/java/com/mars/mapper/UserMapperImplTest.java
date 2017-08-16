package com.mars.mapper;

import com.mars.AbstractSpringTest;
import com.mars.mapper.UserMapperImpl;
import com.mars.models.User;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserMapperImplTest extends AbstractSpringTest{

    @Autowired
    UserMapperImpl userMapper;

    @Test
    public void convertNotNullUserModelToNotNullUserEntity() throws Exception {
        assertThat(userMapper.toEntity(composeUserModel()), new ReflectionEquals(getUserEntity()));
    }

    @Test
    public void convertNullUserModelToNullUserEntity() throws Exception {
        assertNull(userMapper.toEntity(null));
    }

    @Test
    public void convertNotNullUserEntityToNotNullUserModel() throws Exception {
        User expectedUserModel = composeUserModel();
        expectedUserModel.setPassword(null);
        assertThat(userMapper.toModel(getUserEntity()), new ReflectionEquals(expectedUserModel));
    }

    @Test
    public void convertNullUserEntityToNullUserModel() throws Exception {
        assertNull(userMapper.toModel(null));
    }

}