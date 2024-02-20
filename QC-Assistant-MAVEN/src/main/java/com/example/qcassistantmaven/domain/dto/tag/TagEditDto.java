package com.example.qcassistantmaven.domain.dto.tag;

import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;

import java.util.List;
import java.util.stream.Collectors;

public class TagEditDto {

    private Long id;
    private String severity;
    private String text;
    private String type;
    private String orderType;
    private String shellType;
    private String operatingSystem;
    private List<String> destinations;
    private List<String> studies;


    public Long getId() {
        return id;
    }

    public TagEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeverity() {
        return severity;
    }

    public TagEditDto setSeverity(String severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public TagEditDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getType() {
        return type;
    }

    public TagEditDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getOrderType() {
        return orderType;
    }

    public TagEditDto setOrderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getShellType() {
        return shellType;
    }

    public TagEditDto setShellType(String shellType) {
        this.shellType = shellType;
        return this;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public TagEditDto setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public TagEditDto setDestinations(List<String> destinations) {
        this.destinations = destinations;
        return this;
    }

    public List<String> getStudies() {
        return studies;
    }

    public TagEditDto setStudies(List<String> studies) {
        this.studies = studies;
        return this;
    }

    public <T extends BaseTag> void setSpecialFields(T tag){
        setDestinations(tag.getDestinations().stream()
                .map(Destination::getName)
                .collect(Collectors.toList()));
        setStudies(tag.getStudies().stream()
                .map(BaseStudy::getName)
                .collect(Collectors.toList()));
    }
}
