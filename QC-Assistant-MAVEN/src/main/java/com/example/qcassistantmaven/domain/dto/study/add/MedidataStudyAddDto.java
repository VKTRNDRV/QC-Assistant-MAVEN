package com.example.qcassistantmaven.domain.dto.study.add;

import com.example.qcassistantmaven.domain.dto.study.environment.add.MedidataEnvironmentAddDto;

public class MedidataStudyAddDto {

    private String name;
    private String folderURL;
    private String sponsor;
    private MedidataEnvironmentAddDto environment;
    private String containsTranslatedLabels;
    private String containsTranslatedDocs;
    private String containsEditableWelcomeLetter;
    private String isPatientDeviceIpad;
    private String includesHeadphonesStyluses;




    public String getName() {
        return name;
    }

    public MedidataStudyAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public MedidataStudyAddDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public MedidataStudyAddDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public MedidataEnvironmentAddDto getEnvironment() {
        return environment;
    }

    public MedidataStudyAddDto setEnvironment(MedidataEnvironmentAddDto environment) {
        this.environment = environment;
        return this;
    }

    public String getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public MedidataStudyAddDto setContainsTranslatedLabels(String containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public String getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public MedidataStudyAddDto setContainsTranslatedDocs(String containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public String getContainsEditableWelcomeLetter() {
        return containsEditableWelcomeLetter;
    }

    public MedidataStudyAddDto setContainsEditableWelcomeLetter(String containsEditableWelcomeLetter) {
        this.containsEditableWelcomeLetter = containsEditableWelcomeLetter;
        return this;
    }

    public String getIsPatientDeviceIpad() {
        return isPatientDeviceIpad;
    }

    public MedidataStudyAddDto setIsPatientDeviceIpad(String isPatientDeviceIpad) {
        this.isPatientDeviceIpad = isPatientDeviceIpad;
        return this;
    }

    public String getIncludesHeadphonesStyluses() {
        return includesHeadphonesStyluses;
    }

    public MedidataStudyAddDto setIncludesHeadphonesStyluses(String includesHeadphonesStyluses) {
        this.includesHeadphonesStyluses = includesHeadphonesStyluses;
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
