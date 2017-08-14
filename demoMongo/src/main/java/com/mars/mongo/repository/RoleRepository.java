package com.mars.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mars.mongo.repository.entity.Role;


@Repository
public interface RoleRepository extends MongoRepository<Role, String>
{
	
	Role findByName(String name);
}
