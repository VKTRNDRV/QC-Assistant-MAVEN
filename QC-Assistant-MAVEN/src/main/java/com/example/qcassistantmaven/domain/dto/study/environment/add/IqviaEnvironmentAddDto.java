package com.example.qcassistantmaven.domain.dto.study.environment.add;

import java.util.List;

public class IqviaEnvironmentAddDto {

    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private List<String> patientApps;
    private List<String> siteApps;



    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public IqviaEnvironmentAddDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public IqviaEnvironmentAddDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public IqviaEnvironmentAddDto setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public IqviaEnvironmentAddDto setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public IqviaEnvironmentAddDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }
}
