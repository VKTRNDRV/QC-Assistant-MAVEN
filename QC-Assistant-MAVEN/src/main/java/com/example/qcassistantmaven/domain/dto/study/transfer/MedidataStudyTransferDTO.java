package com.example.qcassistantmaven.domain.dto.study.transfer;

import com.example.qcassistantmaven.domain.dto.study.environment.transfer.MedidataEnvironmentTransferDTO;
import com.google.gson.annotations.Expose;

public class MedidataStudyTransferDTO extends BaseStudyTransferDTO{

    @Expose
    private MedidataEnvironmentTransferDTO environment;

    @Expose
    private String containsTranslatedLabels;

    @Expose
    private String containsTranslatedDocs;

    @Expose
    private String containsEditableWelcomeLetter;

    @Expose
    private String isPatientDeviceIpad;

    @Expose
    private String includesHeadphonesStyluses;



    public MedidataEnvironmentTransferDTO getEnvironment() {
        return environment;
    }

    public MedidataStudyTransferDTO setEnvironment(MedidataEnvironmentTransferDTO environment) {
        this.environment = environment;
        return this;
    }

    public String getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public MedidataStudyTransferDTO setContainsTranslatedLabels(String containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public String getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public MedidataStudyTransferDTO setContainsTranslatedDocs(String containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public String getContainsEditableWelcomeLetter() {
        return containsEditableWelcomeLetter;
    }

    public MedidataStudyTransferDTO setContainsEditableWelcomeLetter(String containsEditableWelcomeLetter) {
        this.containsEditableWelcomeLetter = containsEditableWelcomeLetter;
        return this;
    }

    public String getIsPatientDeviceIpad() {
        return isPatientDeviceIpad;
    }

    public MedidataStudyTransferDTO setIsPatientDeviceIpad(String isPatientDeviceIpad) {
        this.isPatientDeviceIpad = isPatientDeviceIpad;
        return this;
    }

    public String getIncludesHeadphonesStyluses() {
        return includesHeadphonesStyluses;
    }

    public MedidataStudyTransferDTO setIncludesHeadphonesStyluses(String includesHeadphonesStyluses) {
        this.includesHeadphonesStyluses = includesHeadphonesStyluses;
        return this;
    }
}
