package com.mars.service;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mars.AbstractTest;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.RoleMapper;
import com.mars.models.Role;
import com.mars.repository.RoleEntityRepository;
import com.mars.repository.entity.RoleEntity;
import com.mars.service.RoleServiceImpl;

public class RoleServiceImplTest extends AbstractTest
{

    @Mock
    RoleEntityRepository roleEntityRepository;

    @Mock
    RoleMapper roleMapper;

    @Spy
    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    public void returnsRoleById() throws Exception
    {
        RoleEntity roleEntity = new RoleEntity();
        Role role = new Role();
        when(roleEntityRepository.findOne(1)).thenReturn(roleEntity);
        when(roleMapper.toModel(roleEntity)).thenReturn(role);
        assertSame(role, roleService.findById("1"));

        verify(roleEntityRepository).findOne(1);
        verify(roleMapper).toModel(roleEntity);
        verifyNoMoreInteractions(roleEntityRepository, roleMapper);
    }

    @Test
    public void throwNotFoundExceptionIfIdIsNull() throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString("Could not find Role by id"));
        thrown.expectMessage(containsString("[1:id=null]"));

        roleService.findById(null);
    }

    @Test
    public void throwNotFoundExceptionIfRoleDoesNotExists() throws Exception
    {
        when(roleEntityRepository.findOne(1)).thenReturn(null);
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString("Could not find Role by id"));
        thrown.expectMessage(containsString("[1:id=1]"));

        roleService.findById("1");
    }

    @Test
    public void findAllRolesByPages() throws Exception
    {
        PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "name");
        RoleEntity roleEntity = new RoleEntity();
        Role role = new Role();

        when(roleService.getPageRequest(0, 1, "name", "asc")).thenReturn(pageRequest);
        when(roleEntityRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(asList(roleEntity)));
        when(roleMapper.toModel(roleEntity)).thenReturn(role);

        Page<Role> all = roleService.findAll(0, 1, "name", "asc");

        assertEquals(1, all.getTotalPages());
        assertEquals(1, all.getTotalElements());
        List<Role> content = all.getContent();
        assertEquals(1, content.size());
        assertSame(role, content.get(0));

        verify(roleService).getPageRequest(0, 1, "name", "asc");
        verify(roleEntityRepository).findAll(pageRequest);
        verify(roleMapper).toModel(roleEntity);
    }
}
