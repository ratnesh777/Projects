package com.mars.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(value = Include.NON_NULL)
public class Login
{

    @NotEmpty
    @Email
    @Getter(AccessLevel.NONE)
    String email;

    @NotEmpty
    String password;

    String role;

    String firstName;

    String lastName;

    public String getEmail()
    {
        return email == null ? null : email.toLowerCase();
    }
}
