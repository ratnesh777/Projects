package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.models.Product;

/**
 * Copyright (c) 2017 IPC Systems, Inc. Created by Viktor Bondarenko on 1/23/2017.
 */
public interface ProductService
{
    Product findById(String id);

    Page<Product> findAll(Integer page, Integer size, String sortParam, String sortDirection);
}
