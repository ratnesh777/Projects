package com.mars.models;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Viktor Bondarenko on 12/15/2016.
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BackRoom
{
    private static final String IP_ADDRESS_REGEXP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    private static final String WRONG_IP_ADDRESS_MESSAGE = "should be valid IPv4 address";

    private String id;

    @NotBlank
    private String name;

    @Pattern(regexp = IP_ADDRESS_REGEXP, message = WRONG_IP_ADDRESS_MESSAGE)
    private String homeZoneIP;

    @Pattern(regexp = IP_ADDRESS_REGEXP, message = WRONG_IP_ADDRESS_MESSAGE)
    private String voipProxyIP;

    @Pattern(regexp = IP_ADDRESS_REGEXP, message = WRONG_IP_ADDRESS_MESSAGE)
    private String managementProxyIP;

    @NotNull
    private Company company;

    private List<User> users;
}
