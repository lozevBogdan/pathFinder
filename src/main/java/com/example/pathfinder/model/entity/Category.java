package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private LocalDateTime name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Category() {
    }

    public LocalDateTime getName() {
        return name;
    }

    public Category setName(LocalDateTime name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }
}
