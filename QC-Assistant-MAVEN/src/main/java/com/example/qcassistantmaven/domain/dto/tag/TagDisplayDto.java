package com.example.qcassistantmaven.domain.dto.tag;

import java.util.List;

public class TagDisplayDto {

    private Long id;
    private String severity;
    private String text;
    private String type;
    private String orderType;
    private String shellType;
    private String operatingSystem;
    private String destinations;
    private String studies;

    public Long getId() {
        return id;
    }

    public TagDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeverity() {
        return severity;
    }

    public TagDisplayDto setSeverity(String severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public TagDisplayDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getType() {
        return type;
    }

    public TagDisplayDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public TagDisplayDto setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getShellType() {
        return shellType;
    }

    public TagDisplayDto setShellType(String shellType) {
        this.shellType = shellType;
        return this;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public TagDisplayDto setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public String getDestinations() {
        return destinations;
    }

    public TagDisplayDto setDestinations(String destinations) {
        this.destinations = destinations;
        return this;
    }

    public String getStudies() {
        return studies;
    }

    public TagDisplayDto setStudies(String studies) {
        this.studies = studies;
        return this;
    }
}
