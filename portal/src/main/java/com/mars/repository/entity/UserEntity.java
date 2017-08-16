package com.mars.repository.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

@Data
@EqualsAndHashCode(exclude = { "company", "backRooms", "products" })
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "email_UniqueConstraint", columnNames = {
        "email" }) )
public class UserEntity extends AbstractEntity
{

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "site_id", nullable = false)
    private String siteId;

    @OneToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_product", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "product_id") )
    private Set<ProductEntity> products;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_back_room", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "back_room_id") )
    private Set<BackRoomEntity> backRooms;
    
    @Column(name = "passwd_token", nullable = true)
    private String passwordToken;
}
