package com.mars.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.mars.models.Product;
import com.mars.repository.entity.ProductEntity;

@Service
public class ProductMapperImpl extends AbstractBaseMapper<ProductEntity, Product>
        implements ProductMapper
{
    @Override
    public Product toModel(ProductEntity entity)
    {
        if (entity == null)
        {
            return null;
        }
        Product model = new Product();
        model.setId(entity.getId() != null ? entity.getId().toString() : null);
        model.setName(entity.getName());
        return model;
    }

    @Override
    public ProductEntity toEntity(Product model)
    {
        if (model == null)
        {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(StringUtils.isNumeric(model.getId()) ? Integer.valueOf(model.getId()) : null);
        entity.setName(model.getName());
        return entity;
    }
}
