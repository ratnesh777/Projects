package com.mars.mongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.mars.mongo.repository.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>//,QueryDslPredicateExecutor<User>
{
    User findByEmail(String email);

    User findByPasswordToken(String passwordToken);
    
    List<User> findByAccountLocked(Boolean accountLocked);
    
    List<User> findByStatus(String status);
}
