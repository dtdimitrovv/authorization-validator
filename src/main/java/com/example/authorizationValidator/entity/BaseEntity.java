package com.example.authorizationValidator.entity;

public class BaseEntity {
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseEntity() {

    }

    public BaseEntity(Long id) {
        this.id = id;
    }
}
