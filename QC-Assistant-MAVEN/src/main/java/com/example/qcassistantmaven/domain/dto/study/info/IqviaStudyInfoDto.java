package com.example.qcassistantmaven.domain.dto.study.info;

import com.example.qcassistantmaven.domain.dto.study.add.IqviaStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.environment.add.IqviaEnvironmentAddDto;
import com.example.qcassistantmaven.domain.dto.study.environment.info.IqviaEnvironmentInfoDto;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;

public class IqviaStudyInfoDto {

    private String name;
    private String folderURL;
    private String sponsor;
    private IqviaEnvironmentInfoDto environment;
    private String containsTranslatedLabels;
    private String containsTranslatedDocs;
    private String containsSepSitePatientLabels;
    private String isGsgPlain;



    public String getName() {
        return name;
    }

    public IqviaStudyInfoDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public IqviaStudyInfoDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public IqviaStudyInfoDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public IqviaEnvironmentInfoDto getEnvironment() {
        return environment;
    }

    public IqviaStudyInfoDto setEnvironment(IqviaEnvironmentInfoDto environment) {
        this.environment = environment;
        return this;
    }

    public String getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public IqviaStudyInfoDto setContainsTranslatedLabels(String containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public String getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public IqviaStudyInfoDto setContainsTranslatedDocs(String containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public String getContainsSepSitePatientLabels() {
        return containsSepSitePatientLabels;
    }

    public IqviaStudyInfoDto setContainsSepSitePatientLabels(String containsSepSitePatientLabels) {
        this.containsSepSitePatientLabels = containsSepSitePatientLabels;
        return this;
    }

    public String getIsGsgPlain() {
        return isGsgPlain;
    }

    public IqviaStudyInfoDto setIsGsgPlain(String isGsgPlain) {
        this.isGsgPlain = isGsgPlain;
        return this;
    }

    public void setSpecialFields(IqviaStudy study) {
        this.setSponsor(study.getSponsor().getName());
        this.environment.setSpecialFields(study.getEnvironment());
    }
}
