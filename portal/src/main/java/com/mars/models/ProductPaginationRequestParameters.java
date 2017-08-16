package com.mars.models;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ProductPaginationRequestParameters extends PaginationRequestParameters
{
    @Pattern(regexp = "id|name")
    String sortParam;
}
