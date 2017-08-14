package com.mars.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mars.mongo.repository.entity.User;


public interface UserService
{

    User findById(String id);

    Page<User> findAll(User user,Integer page, Integer size, String sortParam, String sortDirection,String searchString);

    User create(User user);

    User updateUser(User user, boolean updateStatusForEmailInvite);

    void deleteAll();

    void delete(List<String> asList);

    
    
}
