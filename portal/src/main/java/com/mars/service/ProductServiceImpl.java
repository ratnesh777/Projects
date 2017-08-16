package com.mars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.ProductMapper;
import com.mars.models.Product;
import com.mars.repository.ProductRepository;
import com.mars.repository.entity.ProductEntity;
import com.mars.util.ErrorMessagesConstant;

/**
 * Copyright (c) 2017 IPC Systems, Inc. Created by Viktor Bondarenko on 1/23/2017.
 */
@Service
public class ProductServiceImpl extends AbstractServiceImpl implements ProductService
{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public Product findById(String id)
    {
        ProductEntity entity = null;
        try
        {
            entity = productRepository.findOne(Integer.parseInt(id));
        }
        catch (Exception ignored)
        {
        }
        if (entity == null)
        {
            throw new ResourceNotFoundException(ErrorMessagesConstant.PRODUCT_ID_NOT_FOUND)
                    .addContextValue(ErrorMessagesConstant.ID, id);

        }
        return productMapper.toModel(entity);
    }

    @Override
    public Page<Product> findAll(Integer page, Integer size, String sortParam, String sortDirection)
    {
        return productRepository.findAll(getPageRequest(page, size, sortParam, sortDirection))
                .map(productEntity -> productMapper.toModel(productEntity));
    }
}
