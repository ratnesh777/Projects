package com.mars.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;

import com.mars.AbstractSpringTest;
import com.mars.mapper.ProductMapperImpl;
import com.mars.models.Product;
import com.mars.repository.entity.ProductEntity;

public class ProductMapperImplTest extends AbstractSpringTest
{

    @Autowired
    ProductMapperImpl productMapper;

    @Test
    public void convertNotNullUserModelToNotNullUserEntity() throws Exception
    {
        Product model = new Product();
        model.setId("1");
        model.setName("role");

        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("role");
        assertThat(productMapper.toEntity(model), new ReflectionEquals(entity));
    }

    @Test
    public void convertNullUserModelToNullUserEntity() throws Exception
    {
        assertNull(productMapper.toEntity(null));
    }

    @Test
    public void convertNotNullUserEntityToNotNullUserModel() throws Exception
    {
        ProductEntity entity = new ProductEntity();
        entity.setId(1);
        entity.setName("role");

        Product model = new Product();
        model.setId("1");
        model.setName("role");
        assertThat(productMapper.toModel(entity), new ReflectionEquals(model));
    }

    @Test
    public void convertNullUserEntityToNullUserModel() throws Exception
    {
        assertNull(productMapper.toModel(null));
    }

}
