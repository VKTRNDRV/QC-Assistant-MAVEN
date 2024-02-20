package com.example.qcassistantmaven.domain.dto.transfer;

public class DestinationLangsTransferDTO {

    private String destinations;

    private String languages;

    public String getDestinations() {
        return destinations;
    }

    public DestinationLangsTransferDTO setDestinations(String destinations) {
        this.destinations = destinations;
        return this;
    }

    public String getLanguages() {
        return languages;
    }

    public DestinationLangsTransferDTO setLanguages(String languages) {
        this.languages = languages;
        return this;
    }
}
