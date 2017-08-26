package com.mars.models;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mars.repository.entity.UserStatus;


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User
{

    private String id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    private String password;

    private UserStatus status;

    @NotBlank
    private String siteId;

    @NotNull
    private Role role;

    private List<String> productIds;

    @NotNull
    private Company company;

    private String locationId;
    
    private String passwordToken;
}
