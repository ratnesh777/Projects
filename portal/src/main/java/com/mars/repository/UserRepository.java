package com.mars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mars.repository.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>
{
	UserEntity findByEmail(String email);
	
	@Query("select u from UserEntity u where u.passwordToken = ?1")
	UserEntity findByPasswordToken(String passwordToken);
}
