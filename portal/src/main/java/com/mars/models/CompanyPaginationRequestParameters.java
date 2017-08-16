package com.mars.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CompanyPaginationRequestParameters extends PaginationRequestParameters {

    @Pattern(regexp = "id|name|")
    String sortParam;

}
