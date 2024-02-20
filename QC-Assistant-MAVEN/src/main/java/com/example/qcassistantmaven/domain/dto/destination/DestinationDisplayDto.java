package com.example.qcassistantmaven.domain.dto.destination;

public class DestinationDisplayDto {

    private Long id;

    private String name;

    private String plugType;

    private String simType;

    private String requiresSpecialModels;

    private String requiresUnusedDevices;

    private String requiresInvoice;

    private String languages;


    public Long getId() {
        return id;
    }

    public DestinationDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DestinationDisplayDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlugType() {
        return plugType;
    }

    public DestinationDisplayDto setPlugType(String plugType) {
        this.plugType = plugType;
        return this;
    }

    public String getSimType() {
        return simType;
    }

    public DestinationDisplayDto setSimType(String simType) {
        this.simType = simType;
        return this;
    }

    public String getRequiresSpecialModels() {
        return requiresSpecialModels;
    }

    public DestinationDisplayDto setRequiresSpecialModels(String requiresSpecialModels) {
        this.requiresSpecialModels = requiresSpecialModels;
        return this;
    }

    public String getRequiresUnusedDevices() {
        return requiresUnusedDevices;
    }

    public DestinationDisplayDto setRequiresUnusedDevices(String requiresUnusedDevices) {
        this.requiresUnusedDevices = requiresUnusedDevices;
        return this;
    }

    public String getRequiresInvoice() {
        return requiresInvoice;
    }

    public DestinationDisplayDto setRequiresInvoice(String requiresInvoice) {
        this.requiresInvoice = requiresInvoice;
        return this;
    }

    public String getLanguages() {
        return languages;
    }

    public DestinationDisplayDto setLanguages(String languages) {
        this.languages = languages;
        return this;
    }
}
