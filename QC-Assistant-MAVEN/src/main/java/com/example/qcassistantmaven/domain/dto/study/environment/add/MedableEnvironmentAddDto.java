package com.example.qcassistantmaven.domain.dto.study.environment.add;

import java.util.List;

public class MedableEnvironmentAddDto {

    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private List<String> patientApps;
    private List<String> siteApps;
    private String containsChinaGroup;



    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public MedableEnvironmentAddDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public MedableEnvironmentAddDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public MedableEnvironmentAddDto setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public MedableEnvironmentAddDto setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getContainsChinaGroup() {
        return containsChinaGroup;
    }

    public MedableEnvironmentAddDto setContainsChinaGroup(String containsChinaGroup) {
        this.containsChinaGroup = containsChinaGroup;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public MedableEnvironmentAddDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }
}
