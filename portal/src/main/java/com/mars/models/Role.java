package com.mars.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Role {
    private String id;
    private String name;

    public Role(String id) {
        this.id = id;
    }
}
