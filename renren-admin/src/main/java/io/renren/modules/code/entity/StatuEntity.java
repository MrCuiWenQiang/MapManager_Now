package io.renren.modules.code.entity;

import lombok.Data;

@Data
public class StatuEntity {
    private int id;
    private String name;

    public StatuEntity() {
    }

    public StatuEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
