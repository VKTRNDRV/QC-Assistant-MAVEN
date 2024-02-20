package com.example.qcassistantmaven.domain.dto.study.environment.info;

import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.util.TrinaryBoolean;

import java.util.List;
import java.util.stream.Collectors;

public class MedidataEnvironmentInfoDto {

    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private String isLegacy;
    private String patientApps;
    private String siteApps;

    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public MedidataEnvironmentInfoDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public MedidataEnvironmentInfoDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public String getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironmentInfoDto setIsLegacy(String isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }

    public String getPatientApps() {
        return patientApps;
    }

    public MedidataEnvironmentInfoDto setPatientApps(String patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public String getSiteApps() {
        return siteApps;
    }

    public MedidataEnvironmentInfoDto setSiteApps(String siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public MedidataEnvironmentInfoDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }

    public void setSpecialFields(MedidataEnvironment environment){
        this.setPatientApps(this.getAppNamesList(environment.getPatientApps()))
                .setSiteApps(this.getAppNamesList(environment.getSiteApps()))
                .setIsLegacy(environment.getIsLegacy().name());
    }

    public String getAppNamesList(List<? extends BaseApp> apps){
        return apps.stream()
                .map(BaseApp::getName)
                .collect(Collectors.joining(", "));
    }
}
