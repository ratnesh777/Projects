package com.mars.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import lombok.Data;


@Data
public class PaginationRequestParameters
{
    @Min(value = 0)
    Integer page;
    @Min(value = 1)
    Integer size;
    @Pattern(regexp = "asc|desc|")
    String sortDirection;
    String searchKey;
}
