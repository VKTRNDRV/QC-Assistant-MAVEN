package com.example.qcassistantmaven.domain.dto.app;

public class AppAddDto {

    private String name;

    private String requiresCamera;


    public String getName() {
        return name;
    }

    public AppAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRequiresCamera() {
        return requiresCamera;
    }

    public AppAddDto setRequiresCamera(String requiresCamera) {
        this.requiresCamera = requiresCamera;
        return this;
    }

    public void trimStringFields() {
        if(this.name == null){
            return;
        }

        this.name = this.name.trim();
    }
}
