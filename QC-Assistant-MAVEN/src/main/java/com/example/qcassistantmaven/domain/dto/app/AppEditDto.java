package com.example.qcassistantmaven.domain.dto.app;

public class AppEditDto {

    private Long id;

    private String name;

    private String requiresCamera;


    public Long getId() {
        return id;
    }

    public AppEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AppEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getRequiresCamera() {
        return requiresCamera;
    }

    public AppEditDto setRequiresCamera(String requiresCamera) {
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
