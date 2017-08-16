package com.mars.models;

import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * Copyright (c) 2017 IPC Systems, Inc. Created by Viktor Bondarenko on 1/20/2017.
 */
@Data
public class RolePaginationRequestParameters extends PaginationRequestParameters
{
    @Pattern(regexp = "id|name")
    String sortParam;
}
