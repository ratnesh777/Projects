package com.mars.models;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * Copyright (c) 2016 IPC Systems, Inc.
 * Created by Viktor Bondarenko on 12/21/2016.
 */
@Data
public class UserPaginationRequestParameters extends PaginationRequestParameters{

    @Pattern(regexp = "id|firstName|lastName|email|status|siteId|role.id|role.name|company.id|company.name|")
    String sortParam;
}
