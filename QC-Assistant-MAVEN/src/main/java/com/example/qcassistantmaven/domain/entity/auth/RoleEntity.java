package com.example.qcassistantmaven.domain.entity.auth;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.enums.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public RoleEntity(RoleEnum role){
        setRole(role);
    }

    public RoleEntity() {

    }

    public RoleEnum getRole() {
        return role;
    }

    public RoleEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}
