package com.example.qcassistantmaven.domain.dto.study.edit;

import com.example.qcassistantmaven.domain.dto.study.environment.add.IqviaEnvironmentAddDto;
import com.example.qcassistantmaven.domain.dto.study.environment.edit.IqviaEnvironmentEditDto;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;

import java.util.stream.Collectors;

public class IqviaStudyEditDto {

    private Long id;
    private String name;
    private String folderURL;
    private String sponsor;
    private IqviaEnvironmentEditDto environment;
    private String containsTranslatedLabels;
    private String containsTranslatedDocs;
    private String containsSepSitePatientLabels;
    private String isGsgPlain;





    public Long getId() {
        return id;
    }

    public IqviaStudyEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IqviaStudyEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public IqviaStudyEditDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public IqviaStudyEditDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public IqviaEnvironmentEditDto getEnvironment() {
        return environment;
    }

    public IqviaStudyEditDto setEnvironment(IqviaEnvironmentEditDto environment) {
        this.environment = environment;
        return this;
    }

    public String getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public IqviaStudyEditDto setContainsTranslatedLabels(String containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public String getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public IqviaStudyEditDto setContainsTranslatedDocs(String containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public String getContainsSepSitePatientLabels() {
        return containsSepSitePatientLabels;
    }

    public IqviaStudyEditDto setContainsSepSitePatientLabels(String containsSepSitePatientLabels) {
        this.containsSepSitePatientLabels = containsSepSitePatientLabels;
        return this;
    }

    public String getIsGsgPlain() {
        return isGsgPlain;
    }

    public IqviaStudyEditDto setManualFields(IqviaStudy study){
        this.setSponsor(study.getSponsor().getName());
        this.environment.setPatientApps(study.getEnvironment().getPatientApps()
                .stream().map(BaseApp::getName).collect(Collectors.toList()));
        this.environment.setSiteApps(study.getEnvironment().getSiteApps()
                .stream().map(BaseApp::getName).collect(Collectors.toList()));
        return this;
    }

    public IqviaStudyEditDto setIsGsgPlain(String isGsgPlain) {
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
