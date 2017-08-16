package com.mars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mars.repository.entity.RoleEntity;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer>{
}
