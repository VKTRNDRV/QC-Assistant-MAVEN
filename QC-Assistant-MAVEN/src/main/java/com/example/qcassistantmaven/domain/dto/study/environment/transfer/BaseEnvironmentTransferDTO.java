package com.example.qcassistantmaven.domain.dto.study.environment.transfer;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BaseEnvironmentTransferDTO {

    @Expose
    private String isSitePatientSeparated;

    @Expose
    private String isDestinationSeparated;

    @Expose
    private String isOsSeparated;

    @Expose
    private List<String> patientApps;

    @Expose
    private List<String> siteApps;



    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public BaseEnvironmentTransferDTO setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public BaseEnvironmentTransferDTO setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public BaseEnvironmentTransferDTO setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public BaseEnvironmentTransferDTO setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public BaseEnvironmentTransferDTO setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }
}
