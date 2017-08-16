package com.mars.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mars.error.ErrorResource;
import com.mars.exception.InvalidRequestException;
import com.mars.exception.ResourceNotFoundException;
import com.mars.models.Product;
import com.mars.models.ProductPaginationRequestParameters;
import com.mars.service.ProductService;
import com.mars.util.APIUtilConstant;
import com.mars.util.ErrorMessagesConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = APIUtilConstant.PRODUCT_API_END_POINT, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Product APIs Detail")
public class ProductController
{

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Get product by Id", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Product.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 404, message = ErrorMessagesConstant.NOT_FOUND, response = ErrorResource.class) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getById(
            @ApiParam(name = "id", value = "product_id", required = true) @PathVariable("id") String id)
                    throws ResourceNotFoundException
    {
        return productService.findById(id);
    }

    @ApiOperation(value = "Find all products by pages", responseContainer = "List", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ErrorMessagesConstant.SUCCESS, response = Product.class),
            @ApiResponse(code = 422, message = ErrorMessagesConstant.INVALID_REQUEST, response = ErrorResource.class),
            @ApiResponse(code = 400, message = ErrorMessagesConstant.BAD_REQUEST, response = ErrorResource.class) })

    @RequestMapping(method = RequestMethod.GET)
    public Page<Product> getAll(
            @Valid @ModelAttribute() ProductPaginationRequestParameters paginationRequestParameters,
            BindingResult bindingResult)
    {

        if (bindingResult.hasErrors())
        {
            throw new InvalidRequestException(ErrorMessagesConstant.INVALID_PARAMETER_PASSED,
                    bindingResult);
        }

        return productService.findAll(paginationRequestParameters.getPage(),
                paginationRequestParameters.getSize(), paginationRequestParameters.getSortParam(),
                paginationRequestParameters.getSortDirection());

    }
}
