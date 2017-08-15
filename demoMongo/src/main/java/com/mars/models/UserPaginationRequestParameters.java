package com.mars.models;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserPaginationRequestParameters extends PaginationRequestParameters
{

    @Pattern(regexp = "id|firstName|lastName|email|status|siteId|role.id|role.name|company.id|company.name|products")
    String sortParam;
}
