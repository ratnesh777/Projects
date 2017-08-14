package com.mars.models;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CustomerPaginationRequestParameters extends PaginationRequestParameters
{

    @Pattern(regexp = "id|name|siteID|softClientURL|ntrServerFQDN|ntrURL|skypeURL|emailDomain|")
    String sortParam;

}
