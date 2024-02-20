package com.example.qcassistantmaven.domain.dto.study.add;

import com.example.qcassistantmaven.domain.dto.study.environment.add.IqviaEnvironmentAddDto;

public class IqviaStudyAddDto {

    private String name;
    private String folderURL;
    private String sponsor;
    private IqviaEnvironmentAddDto environment;
    private String containsTranslatedLabels;
    private String containsTranslatedDocs;
    private String containsSepSitePatientLabels;
    private String isGsgPlain;



    public String getName() {
        return name;
    }

    public IqviaStudyAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public IqviaStudyAddDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public IqviaStudyAddDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public IqviaEnvironmentAddDto getEnvironment() {
        return environment;
    }

    public IqviaStudyAddDto setEnvironment(IqviaEnvironmentAddDto environment) {
        this.environment = environment;
        return this;
    }

    public String getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public IqviaStudyAddDto setContainsTranslatedLabels(String containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public String getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public IqviaStudyAddDto setContainsTranslatedDocs(String containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public String getContainsSepSitePatientLabels() {
        return containsSepSitePatientLabels;
    }

    public IqviaStudyAddDto setContainsSepSitePatientLabels(String containsSepSitePatientLabels) {
        this.containsSepSitePatientLabels = containsSepSitePatientLabels;
        return this;
    }

    public String getIsGsgPlain() {
        return isGsgPlain;
    }

    public IqviaStudyAddDto setIsGsgPlain(String isGsgPlain) {
        this.isGsgPlain = isGsgPlain;
        return this;
    }

    public void trimStringFields() {
        if(name != null){
            name = name.trim();
        }

        if(folderURL != null){
            folderURL = folderURL.trim();
        }
    }
}
