package com.example.pathfinder.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public BaseEntity() {

    }

    public Long getId() {
        return Id;
    }

    public BaseEntity setId(Long id) {
        Id = id;
        return this;
    }
}
