package com.example.qcassistantmaven.domain.dto.destination;

import java.util.List;

public class DestinationAddDto {

    private String name;

    private String plugType;

    private String simType;

    private String requiresSpecialModels;

    private String requiresUnusedDevices;

    private String requiresInvoice;

    private List<String> selectedLanguages;


    public String getName() {
        return name;
    }

    public DestinationAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPlugType() {
        return plugType;
    }

    public DestinationAddDto setPlugType(String plugType) {
        this.plugType = plugType;
        return this;
    }

    public String getSimType() {
        return simType;
    }

    public DestinationAddDto setSimType(String simType) {
        this.simType = simType;
        return this;
    }

    public String getRequiresSpecialModels() {
        return requiresSpecialModels;
    }

    public DestinationAddDto setRequiresSpecialModels(String requiresSpecialModels) {
        this.requiresSpecialModels = requiresSpecialModels;
        return this;
    }

    public String getRequiresUnusedDevices() {
        return requiresUnusedDevices;
    }

    public DestinationAddDto setRequiresUnusedDevices(String requiresUnusedDevices) {
        this.requiresUnusedDevices = requiresUnusedDevices;
        return this;
    }

    public String getRequiresInvoice() {
        return requiresInvoice;
    }

    public DestinationAddDto setRequiresInvoice(String requiresInvoice) {
        this.requiresInvoice = requiresInvoice;
        return this;
    }

    public List<String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public DestinationAddDto setSelectedLanguages(List<String> selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
        return this;
    }
}
