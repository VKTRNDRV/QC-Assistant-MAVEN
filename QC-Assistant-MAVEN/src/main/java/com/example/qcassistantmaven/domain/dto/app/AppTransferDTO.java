package com.example.qcassistantmaven.domain.dto.app;

import com.google.gson.annotations.Expose;

public class AppTransferDTO {

    @Expose
    private String name;

    @Expose
    private String requiresCamera;

    public String getName() {
        return name;
    }

    public AppTransferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getRequiresCamera() {
        return requiresCamera;
    }

    public AppTransferDTO setRequiresCamera(String requiresCamera) {
        this.requiresCamera = requiresCamera;
        return this;
    }
}
