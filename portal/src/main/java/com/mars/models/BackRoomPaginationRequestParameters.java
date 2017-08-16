package com.mars.models;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class BackRoomPaginationRequestParameters extends PaginationRequestParameters
{
    @Pattern(regexp = "id|name|homeZoneIP|voipProxyIP|managementProxyIP")
    String sortParam;
}
