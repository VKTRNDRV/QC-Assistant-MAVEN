package com.example.qcassistantmaven.domain.dto.study.environment.info;

import com.example.qcassistantmaven.domain.dto.study.environment.add.IqviaEnvironmentAddDto;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;

import java.util.List;
import java.util.stream.Collectors;

public class IqviaEnvironmentInfoDto {

    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private String patientApps;
    private String siteApps;



    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public IqviaEnvironmentInfoDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public IqviaEnvironmentInfoDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public String getPatientApps() {
        return patientApps;
    }

    public IqviaEnvironmentInfoDto setPatientApps(String patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public String getSiteApps() {
        return siteApps;
    }

    public IqviaEnvironmentInfoDto setSiteApps(String siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public IqviaEnvironmentInfoDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }

    public void setSpecialFields(IqviaEnvironment environment){
        this.setPatientApps(this.getAppNamesList(environment.getPatientApps()))
                .setSiteApps(this.getAppNamesList(environment.getSiteApps()));
    }

    public String getAppNamesList(List<? extends BaseApp> apps){
        return apps.stream()
                .map(BaseApp::getName)
                .collect(Collectors.joining(", "));
    }
}
