package com.example.qcassistantmaven.domain.dto.tag;

import com.google.gson.annotations.Expose;

import java.util.List;

public class TagTransferDTO {

    @Expose
    private String severity;

    @Expose
    private String text;

    @Expose
    private String type;

    @Expose
    private String orderType;

    @Expose
    private String shellType;

    @Expose
    private String operatingSystem;

    @Expose
    private List<String> destinations;

    @Expose
    private List<String> studies;


    public String getSeverity() {
        return severity;
    }

    public TagTransferDTO setSeverity(String severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public TagTransferDTO setText(String text) {
        this.text = text;
        return this;
    }

    public String getType() {
        return type;
    }

    public TagTransferDTO setType(String type) {
        this.type = type;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public TagTransferDTO setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getShellType() {
        return shellType;
    }

    public TagTransferDTO setShellType(String shellType) {
        this.shellType = shellType;
        return this;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public TagTransferDTO setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public TagTransferDTO setDestinations(List<String> destinations) {
        this.destinations = destinations;
        return this;
    }

    public List<String> getStudies() {
        return studies;
    }

    public TagTransferDTO setStudies(List<String> studies) {
        this.studies = studies;
        return this;
    }
}
