package com.mars.repository.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (c) 2016 IPC Systems, Inc. Created by Ratnesh Srivastava
 */

@Data
@EqualsAndHashCode(exclude={"company", "users"})
@Entity
@Table(name = "back_room", uniqueConstraints = @UniqueConstraint(name = "name_company_UniqueConstraint", columnNames = {
        "name", "company_id" }) )
public class BackRoomEntity extends AbstractEntity
{

    private String name;

    @Column(name = "home_zone_ip")
    private String homeZoneIP;

    @Column(name = "voip_proxy_ip")
    private String voipProxyIP;

    @Column(name = "management_proxy_ip")
    private String managementProxyIP;

    @ManyToOne
    private CompanyEntity company;

    @ManyToMany(mappedBy = "backRooms", fetch = FetchType.EAGER)
    private Set<UserEntity> users;
}
