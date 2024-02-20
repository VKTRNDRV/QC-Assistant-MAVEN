package com.example.qcassistantmaven.domain.dto.destination;

import java.util.List;

public class DestinationEditDto {

    private Long id;

    private String name;

    private String plugType;

    private String simType;

    private String requiresSpecialModels;

    private String requiresUnusedDevices;

    private String requiresInvoice;

    private List<String> selectedLanguages;


    public Long getId() {
        return id;
    }

    public DestinationEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DestinationEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlugType() {
        return plugType;
    }

    public DestinationEditDto setPlugType(String plugType) {
        this.plugType = plugType;
        return this;
    }

    public String getSimType() {
        return simType;
    }

    public DestinationEditDto setSimType(String simType) {
        this.simType = simType;
        return this;
    }

    public String getRequiresSpecialModels() {
        return requiresSpecialModels;
    }

    public DestinationEditDto setRequiresSpecialModels(String requiresSpecialModels) {
        this.requiresSpecialModels = requiresSpecialModels;
        return this;
    }

    public String getRequiresUnusedDevices() {
        return requiresUnusedDevices;
    }

    public DestinationEditDto setRequiresUnusedDevices(String requiresUnusedDevices) {
        this.requiresUnusedDevices = requiresUnusedDevices;
        return this;
    }

    public String getRequiresInvoice() {
        return requiresInvoice;
    }

    public DestinationEditDto setRequiresInvoice(String requiresInvoice) {
        this.requiresInvoice = requiresInvoice;
        return this;
    }

    public List<String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public DestinationEditDto setSelectedLanguages(List<String> selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
        return this;
    }
}
