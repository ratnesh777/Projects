package com.mars.service;

import org.springframework.data.domain.Page;

import com.mars.models.Product;

public interface ProductService
{
    Product findById(String id);

    Page<Product> findAll(Integer page, Integer size, String sortParam, String sortDirection);
}
