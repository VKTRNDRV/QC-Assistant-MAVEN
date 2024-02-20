package com.example.qcassistantmaven.domain.dto.study.edit;

import com.example.qcassistantmaven.domain.dto.study.environment.edit.MedableEnvironmentEditDto;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import org.springframework.security.core.parameters.P;

import java.util.stream.Collectors;

public class MedableStudyEditDto {

    private Long id;
    private String name;
    private String folderURL;
    private String sponsor;
    private MedableEnvironmentEditDto environment;

    public Long getId() {
        return id;
    }

    public MedableStudyEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedableStudyEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public MedableStudyEditDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public MedableStudyEditDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public MedableEnvironmentEditDto getEnvironment() {
        return environment;
    }

    public MedableStudyEditDto setEnvironment(MedableEnvironmentEditDto environment) {
        this.environment = environment;
        return this;
    }

    public MedableStudyEditDto setManualFields(MedableStudy study){
        this.setSponsor(study.getSponsor().getName());
        this.environment.setSiteApps(study.getEnvironment().getSiteApps()
                .stream().map(BaseApp::getName).collect(Collectors.toList()));
        this.environment.setPatientApps(study.getEnvironment().getPatientApps()
                .stream().map(BaseApp::getName).collect(Collectors.toList()));
        this.environment.setContainsChinaGroup(study
                .getEnvironment().getContainsChinaGroup().name());
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
