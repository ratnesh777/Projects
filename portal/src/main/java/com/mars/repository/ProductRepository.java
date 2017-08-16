package com.mars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mars.repository.entity.ProductEntity;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Viktor Bondarenko on 12/16/2016.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>
{
}