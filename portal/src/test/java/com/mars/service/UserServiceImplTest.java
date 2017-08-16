package com.mars.service;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mars.AbstractTest;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.CompanyMapper;
import com.mars.mapper.RoleMapper;
import com.mars.mapper.UserMapperImpl;
import com.mars.models.Company;
import com.mars.models.Role;
import com.mars.models.User;
import com.mars.repository.UserRepository;
import com.mars.repository.entity.CompanyEntity;
import com.mars.repository.entity.RoleEntity;
import com.mars.repository.entity.UserEntity;
import com.mars.repository.entity.UserStatus;
import com.mars.service.CompanyService;
import com.mars.service.RoleService;
import com.mars.service.UserServiceImpl;

public class UserServiceImplTest extends AbstractTest
{

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapperImpl userMapper;

    @Mock
    RoleMapper roleMapper;

    @Mock
    RoleService roleService;

    @Mock
    CompanyMapper companyMapper;

    @Mock
    CompanyService companyService;

    @InjectMocks
    @Spy
    UserServiceImpl userService;

    @Test
    public void returnsUserByIdWithoutPassword() throws Exception
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("some password");
        User user = new User();
        when(userRepository.findOne(1)).thenReturn(userEntity);
        when(userMapper.toModel(userEntity)).thenReturn(user);
        assertSame(user, userService.findById("1"));

        verify(userRepository).findOne(1);
        verify(userMapper).toModel(userEntity);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    public void returnsUserByIdWithPassword() throws Exception
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("some password");
        User user = new User();
        when(userRepository.findOne(1)).thenReturn(userEntity);
        when(userMapper.toModel(userEntity)).thenReturn(user);
        User expectedUser = new User();
        expectedUser.setPassword("some password");
        assertThat(userService.findByIdWithPassword("1"), new ReflectionEquals(expectedUser));

        verify(userRepository).findOne(1);
        verify(userMapper).toModel(userEntity);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    public void throwNotFoundExceptionIfIdIsNull() throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString("Could not find User by id"));
        thrown.expectMessage(containsString("[1:id=null]"));

        userService.findById(null);
    }

    @Test
    public void throwNotFoundExceptionIfUserDoesNotExists() throws Exception
    {
        when(userRepository.findOne(1)).thenReturn(null);
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString("Could not find User by id"));
        thrown.expectMessage(containsString("[1:id=1]"));

        userService.findById("1");
    }

    @Test
    public void saveUserToDB() throws Exception
    {
        User modelToSave = new User();
        Role roleModelToSave = new Role();
        roleModelToSave.setId("2");
        roleModelToSave.setName("some role name");
        modelToSave.setRole(roleModelToSave);
        Company companyModelToSave = new Company();
        companyModelToSave.setId("3");
        companyModelToSave.setName("some company name");
        modelToSave.setCompany(companyModelToSave);

        UserEntity convertedEntityToSave = new UserEntity();
        RoleEntity convertedRoleToAdd = new RoleEntity();
        convertedRoleToAdd.setId(2);
        convertedRoleToAdd.setName("some role name");
        convertedEntityToSave.setRole(convertedRoleToAdd);
        CompanyEntity convertedCompanyToAdd = new CompanyEntity();
        convertedCompanyToAdd.setId(3);
        convertedCompanyToAdd.setName("some company name");
        convertedEntityToSave.setCompany(convertedCompanyToAdd);

        Role roleModelToAdd = new Role();
        roleModelToAdd.setId("2");
        roleModelToAdd.setName("actual role name");

        Company companyModelToAdd = new Company();
        companyModelToAdd.setId("3");
        companyModelToAdd.setName("actual company name");

        UserEntity entityToSave = new UserEntity();
        RoleEntity roleToAdd = new RoleEntity();
        roleToAdd.setId(2);
        roleToAdd.setName("actual role name");
        entityToSave.setRole(roleToAdd);
        CompanyEntity companyToAdd = new CompanyEntity();
        companyToAdd.setId(3);
        companyToAdd.setName("actual role name");
        entityToSave.setCompany(companyToAdd);
        entityToSave.setStatus(UserStatus.CREATED);

        UserEntity savedEntity = new UserEntity();
        savedEntity.setRole(roleToAdd);
        savedEntity.setStatus(UserStatus.CREATED);
        savedEntity.setPassword("some password");

        User savedModel = new User();
        Role savedRole = new Role();
        savedRole.setId("2");
        savedRole.setId("actual role name");
        savedModel.setRole(savedRole);
        savedModel.setStatus(UserStatus.CREATED);

        when(userMapper.toEntity(modelToSave)).thenReturn(convertedEntityToSave);
        when(roleService.findById("2")).thenReturn(roleModelToAdd);
        when(roleMapper.toEntity(roleModelToAdd)).thenReturn(roleToAdd);
        when(companyService.findCompanyById("3")).thenReturn(companyModelToAdd);
        when(companyMapper.toEntity(companyModelToAdd)).thenReturn(companyToAdd);
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);
        when(userMapper.toModel(savedEntity)).thenReturn(savedModel);

        assertSame(savedModel, userService.create(modelToSave));

        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityCaptor.capture());
        verify(userMapper).toEntity(modelToSave);
        verify(userMapper).toModel(savedEntity);
        verify(roleService).findById("2");
        verify(roleMapper).toEntity(roleModelToAdd);
        verify(companyService).findCompanyById("3");
        verify(companyMapper).toEntity(companyModelToAdd);
        verifyNoMoreInteractions(userRepository, roleService, companyService, userMapper,
                roleMapper, companyMapper);
        List<UserEntity> allValues = userEntityCaptor.getAllValues();
        assertEquals(1, allValues.size());
        UserEntity actualEntityToSave = allValues.get(0);
        assertThat(actualEntityToSave, new ReflectionEquals(entityToSave, "password"));
        assertNotNull(actualEntityToSave.getPassword());
    }

    @Test
    public void findAllUsersByPages() throws Exception
    {
        PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "email");
        UserEntity userEntity = new UserEntity();
        User user = new User();

        when(userService.getPageRequest(0, 1, "email", "asc")).thenReturn(pageRequest);
        when(userRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(asList(userEntity)));
        when(userMapper.toModel(userEntity)).thenReturn(user);

        Page<User> all = userService.findAll(0, 1, "email", "asc");

        assertEquals(1, all.getTotalPages());
        assertEquals(1, all.getTotalElements());
        List<User> content = all.getContent();
        assertEquals(1, content.size());
        assertSame(user, content.get(0));

        verify(userService).getPageRequest(0, 1, "email", "asc");
        verify(userRepository).findAll(pageRequest);
        verify(userMapper).toModel(userEntity);
    }
}
