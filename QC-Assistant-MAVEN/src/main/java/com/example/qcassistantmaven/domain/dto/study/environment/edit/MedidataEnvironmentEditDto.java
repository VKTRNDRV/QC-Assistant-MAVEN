package com.example.qcassistantmaven.domain.dto.study.environment.edit;

import java.util.List;

public class MedidataEnvironmentEditDto {

    private Long id;
    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private String isLegacy;
    private List<String> patientApps;
    private List<String> siteApps;





    public Long getId() {
        return id;
    }

    public MedidataEnvironmentEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public MedidataEnvironmentEditDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public MedidataEnvironmentEditDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public String getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironmentEditDto setIsLegacy(String isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public MedidataEnvironmentEditDto setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public MedidataEnvironmentEditDto setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public MedidataEnvironmentEditDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }
}
