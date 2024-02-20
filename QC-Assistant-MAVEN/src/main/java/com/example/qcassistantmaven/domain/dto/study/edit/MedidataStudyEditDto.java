package com.example.qcassistantmaven.domain.dto.study.edit;

import com.example.qcassistantmaven.domain.dto.study.environment.edit.MedidataEnvironmentEditDto;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;

import java.util.stream.Collectors;

public class MedidataStudyEditDto {

    private Long id;

    private String name;

    private String folderURL;

    private String sponsor;

    private MedidataEnvironmentEditDto environment;

    private String containsTranslatedLabels;

    private String containsTranslatedDocs;

    private String containsEditableWelcomeLetter;

    private String isPatientDeviceIpad;

    private String includesHeadphonesStyluses;






    public Long getId() {
        return id;
    }

    public MedidataStudyEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedidataStudyEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public MedidataStudyEditDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public MedidataStudyEditDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public MedidataEnvironmentEditDto getEnvironment() {
        return environment;
    }

    public MedidataStudyEditDto setEnvironment(MedidataEnvironmentEditDto environment) {
        this.environment = environment;
        return this;
    }

    public String getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public MedidataStudyEditDto setContainsTranslatedLabels(String containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public String getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public MedidataStudyEditDto setContainsTranslatedDocs(String containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public String getContainsEditableWelcomeLetter() {
        return containsEditableWelcomeLetter;
    }

    public MedidataStudyEditDto setContainsEditableWelcomeLetter(String containsEditableWelcomeLetter) {
        this.containsEditableWelcomeLetter = containsEditableWelcomeLetter;
        return this;
    }

    public String getIsPatientDeviceIpad() {
        return isPatientDeviceIpad;
    }

    public MedidataStudyEditDto setIsPatientDeviceIpad(String isPatientDeviceIpad) {
        this.isPatientDeviceIpad = isPatientDeviceIpad;
        return this;
    }

    public String getIncludesHeadphonesStyluses() {
        return includesHeadphonesStyluses;
    }

    public MedidataStudyEditDto setIncludesHeadphonesStyluses(String includesHeadphonesStyluses) {
        this.includesHeadphonesStyluses = includesHeadphonesStyluses;
        return this;
    }

    public MedidataStudyEditDto setManualFields(MedidataStudy study){
        setSponsor(study.getSponsor().getName());
        this.environment.setPatientApps(study.getEnvironment().getPatientApps()
                .stream().map(BaseApp::getName).collect(Collectors.toList()));
        this.environment.setSiteApps(study.getEnvironment().getSiteApps()
                .stream().map(BaseApp::getName).collect(Collectors.toList()));
        this.environment.setIsLegacy(study.getEnvironment().getIsLegacy().name());
        return this;
    }

    public void trimStringFields() {
        if(name != null){
            name = name.trim();
        }

        if(folderURL != null){
            folderURL = folderURL.trim();
        }
    }
}
