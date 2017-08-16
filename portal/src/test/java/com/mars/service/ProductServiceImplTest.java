package com.mars.service;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mars.AbstractTest;
import com.mars.exception.ResourceNotFoundException;
import com.mars.mapper.ProductMapper;
import com.mars.models.Product;
import com.mars.repository.ProductRepository;
import com.mars.repository.entity.ProductEntity;
import com.mars.service.ProductServiceImpl;

public class ProductServiceImplTest extends AbstractTest
{
    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @Spy
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    public void returnsProductById() throws Exception
    {
        ProductEntity productEntity = new ProductEntity();
        Product product = new Product();
        when(productRepository.findOne(1)).thenReturn(productEntity);
        when(productMapper.toModel(productEntity)).thenReturn(product);
        assertSame(product, productService.findById("1"));

        verify(productRepository).findOne(1);
        verify(productMapper).toModel(productEntity);
        verifyNoMoreInteractions(productRepository, productMapper);
    }

    @Test
    public void throwNotFoundExceptionIfIdIsNull() throws Exception
    {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString("Could not find Product by id"));
        thrown.expectMessage(containsString("[1:id=null]"));

        productService.findById(null);
    }

    @Test
    public void throwNotFoundExceptionIfProductDoesNotExists() throws Exception
    {
        when(productRepository.findOne(1)).thenReturn(null);
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(containsString("Could not find Product by id"));
        thrown.expectMessage(containsString("[1:id=1]"));

        productService.findById("1");
    }

    @Test
    public void findAllProductsByPages() throws Exception
    {
        PageRequest pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "name");
        ProductEntity productEntity = new ProductEntity();
        Product product = new Product();

        when(productService.getPageRequest(0, 1, "name", "asc")).thenReturn(pageRequest);
        when(productRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(asList(productEntity)));
        when(productMapper.toModel(productEntity)).thenReturn(product);

        Page<Product> all = productService.findAll(0, 1, "name", "asc");

        assertEquals(1, all.getTotalPages());
        assertEquals(1, all.getTotalElements());
        List<Product> content = all.getContent();
        assertEquals(1, content.size());
        assertSame(product, content.get(0));

        verify(productService).getPageRequest(0, 1, "name", "asc");
        verify(productRepository).findAll(pageRequest);
        verify(productMapper).toModel(productEntity);
    }

}
