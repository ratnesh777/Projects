package com.mars.models;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserPaginationRequestParameters extends PaginationRequestParameters{

    @Pattern(regexp = "id|firstName|lastName|email|status|siteId|role.id|role.name|company.id|company.name|")
    String sortParam;
}
