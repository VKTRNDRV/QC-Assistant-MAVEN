package com.example.qcassistantmaven.domain.dto.tag;

import java.util.ArrayList;
import java.util.List;

public class TagAddDto {

    private String severity;
    private String text;
    private String type;
    private String orderType;
    private String shellType;
    private String operatingSystem;
    private List<String> destinations;
    private List<String> studies;


    public TagAddDto(){
        this.destinations = new ArrayList<>();
        this.studies = new ArrayList<>();
    }


    public String getSeverity() {
        return severity;
    }

    public TagAddDto setSeverity(String severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public TagAddDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getType() {
        return type;
    }

    public TagAddDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public TagAddDto setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getShellType() {
        return shellType;
    }

    public TagAddDto setShellType(String shellType) {
        this.shellType = shellType;
        return this;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public TagAddDto setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public TagAddDto setDestinations(List<String> destinations) {
        this.destinations = destinations;
        return this;
    }

    public List<String> getStudies() {
        return studies;
    }

    public TagAddDto setStudies(List<String> studies) {
        this.studies = studies;
        return this;
    }
}
