package com.example.authorizationValidator.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class WithoutPrimaryKeyBaseEntity extends BaseEntity {
    @Override
    @Id
    public Long getId() {
        return super.getId();
    }

    public WithoutPrimaryKeyBaseEntity() {
    }

    public WithoutPrimaryKeyBaseEntity(Long id) {
        super(id);
    }
}
