package com.example.qcassistantmaven.domain.dto.transfer;

public class ClinicalEntitiesTransferDTO {

    private String tags;

    private String studies;

    private String sponsors;

    private String apps;



    public String getTags() {
        return tags;
    }

    public ClinicalEntitiesTransferDTO setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getStudies() {
        return studies;
    }

    public ClinicalEntitiesTransferDTO setStudies(String studies) {
        this.studies = studies;
        return this;
    }

    public String getSponsors() {
        return sponsors;
    }

    public ClinicalEntitiesTransferDTO setSponsors(String sponsors) {
        this.sponsors = sponsors;
        return this;
    }

    public String getApps() {
        return apps;
    }

    public ClinicalEntitiesTransferDTO setApps(String apps) {
        this.apps = apps;
        return this;
    }
}
