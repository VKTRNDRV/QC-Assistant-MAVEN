package com.example.qcassistantmaven.domain.dto.study.environment.add;

import java.util.List;

public class MedidataEnvironmentAddDto {

    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private String isLegacy;
    private List<String> patientApps;
    private List<String> siteApps;





    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public MedidataEnvironmentAddDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public MedidataEnvironmentAddDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public String getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironmentAddDto setIsLegacy(String isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public MedidataEnvironmentAddDto setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public MedidataEnvironmentAddDto setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public MedidataEnvironmentAddDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }
}
