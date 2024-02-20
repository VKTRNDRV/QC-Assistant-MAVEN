package com.example.qcassistantmaven.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public static final String UNKNOWN = "UNKNOWN";

    public BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean equalsEntity(BaseEntity baseEntity) {
        if (this == baseEntity) {
            return true;
        }
        if (baseEntity == null || this.getClass() != baseEntity.getClass()) {
            return false;
        }
        return Objects.equals(this.getId(), baseEntity.getId());
    }
}
