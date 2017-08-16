package com.mars.models;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * Copyright (c) 2017 IPC Systems, Inc. Created by Viktor Bondarenko on 1/5/2017.
 */
@Data
public class BackRoomPaginationRequestParameters extends PaginationRequestParameters
{
    @Pattern(regexp = "id|name|homeZoneIP|voipProxyIP|managementProxyIP")
    String sortParam;
}
