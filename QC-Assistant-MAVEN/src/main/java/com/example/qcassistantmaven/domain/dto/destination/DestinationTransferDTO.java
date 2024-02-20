package com.example.qcassistantmaven.domain.dto.destination;

import com.example.qcassistantmaven.domain.dto.language.LanguageTransferDTO;
import com.google.gson.annotations.Expose;
import java.util.List;

public class DestinationTransferDTO {

    @Expose
    private String name;

    @Expose
    private List<LanguageTransferDTO> languages;

    @Expose
    private String plugType;

    @Expose
    private String simType;

    @Expose
    private String requiresSpecialModels;

    @Expose
    private String requiresUnusedDevices;

    @Expose
    private String requiresInvoice;



    public String getName() {
        return name;
    }

    public DestinationTransferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<LanguageTransferDTO> getLanguages() {
        return languages;
    }

    public DestinationTransferDTO setLanguages(List<LanguageTransferDTO> languages) {
        this.languages = languages;
        return this;
    }

    public String getPlugType() {
        return plugType;
    }

    public DestinationTransferDTO setPlugType(String plugType) {
        this.plugType = plugType;
        return this;
    }

    public String getSimType() {
        return simType;
    }

    public DestinationTransferDTO setSimType(String simType) {
        this.simType = simType;
        return this;
    }

    public String getRequiresSpecialModels() {
        return requiresSpecialModels;
    }

    public DestinationTransferDTO setRequiresSpecialModels(String requiresSpecialModels) {
        this.requiresSpecialModels = requiresSpecialModels;
        return this;
    }

    public String getRequiresUnusedDevices() {
        return requiresUnusedDevices;
    }

    public DestinationTransferDTO setRequiresUnusedDevices(String requiresUnusedDevices) {
        this.requiresUnusedDevices = requiresUnusedDevices;
        return this;
    }

    public String getRequiresInvoice() {
        return requiresInvoice;
    }

    public DestinationTransferDTO setRequiresInvoice(String requiresInvoice) {
        this.requiresInvoice = requiresInvoice;
        return this;
    }
}
