package com.example.qcassistantmaven.domain.dto.study.environment.edit;

import java.util.List;

public class MedableEnvironmentEditDto {

    private Long id;
    private String isSitePatientSeparated;
    private String isDestinationSeparated;
    private String isOsSeparated;
    private List<String> patientApps;
    private List<String> siteApps;
    private String containsChinaGroup;



    public Long getId() {
        return id;
    }

    public MedableEnvironmentEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public MedableEnvironmentEditDto setIsSitePatientSeparated(String isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public String getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public MedableEnvironmentEditDto setIsDestinationSeparated(String isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public List<String> getPatientApps() {
        return patientApps;
    }

    public MedableEnvironmentEditDto setPatientApps(List<String> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    public List<String> getSiteApps() {
        return siteApps;
    }

    public MedableEnvironmentEditDto setSiteApps(List<String> siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public String getContainsChinaGroup() {
        return containsChinaGroup;
    }

    public MedableEnvironmentEditDto setContainsChinaGroup(String containsChinaGroup) {
        this.containsChinaGroup = containsChinaGroup;
        return this;
    }

    public String getIsOsSeparated() {
        return isOsSeparated;
    }

    public MedableEnvironmentEditDto setIsOsSeparated(String isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }
}
