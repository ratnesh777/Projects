package com.mars.models;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (c) 2016 IPC Systems, Inc.
 * Created by Viktor Bondarenko on 12/15/2016.
 */
@Data
@NoArgsConstructor
public class Role {
    private String id;
    private String name;

    public Role(String id) {
        this.id = id;
    }
}
