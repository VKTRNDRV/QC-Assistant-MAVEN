package com.example.qcassistantmaven.domain.entity.app;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseApp extends BaseEntity {

    @Column(nullable = false,
            unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_camera")
    private TrinaryBoolean requiresCamera;


    public String getName() {
        return name;
    }

    public BaseApp setName(String name) {
        this.name = name;
        return this;
    }

    public TrinaryBoolean getRequiresCamera() {
        return requiresCamera;
    }

    public BaseApp setRequiresCamera(TrinaryBoolean requiresCamera) {
        this.requiresCamera = requiresCamera;
        return this;
    }
}
