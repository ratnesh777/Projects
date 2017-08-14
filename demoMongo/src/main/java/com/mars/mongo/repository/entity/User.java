package com.mars.mongo.repository.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Document
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    @Indexed(unique = true)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String email;

    private String password;

    private String status;

    private String siteId;

    @NotNull
    private Role role;

 
    private Customer company;

    private String locationId;

    private String passwordToken;

    private Date passwordTokenExpirationDate;

    private boolean accountLocked;

    private String loginCount = "0";
    
    private String unigyVIP;
    
    
    @JsonIgnore
    private String pingId;
    
    private String productName;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(role.getName())));
    }

    @Override
    @JsonIgnore
    public String getUsername()
    {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled()
    {
        return true;
    }

    public String getEmail()
    {
        return email == null ? null : email.toLowerCase();
    }

    public void setEmail(String email)
    {
        this.email = email == null ? null : email.toLowerCase();
    }
}
