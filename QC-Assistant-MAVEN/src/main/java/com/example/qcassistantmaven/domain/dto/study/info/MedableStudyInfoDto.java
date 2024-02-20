package com.example.qcassistantmaven.domain.dto.study.info;

import com.example.qcassistantmaven.domain.dto.study.environment.info.MedableEnvironmentInfoDto;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;

public class MedableStudyInfoDto {

    private String name;
    private String folderURL;
    private String sponsor;
    private MedableEnvironmentInfoDto environment;


    public String getSponsor() {
        return sponsor;
    }

    public MedableStudyInfoDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public MedableEnvironmentInfoDto getEnvironment() {
        return environment;
    }

    public MedableStudyInfoDto setEnvironment(MedableEnvironmentInfoDto environment) {
        this.environment = environment;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedableStudyInfoDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public MedableStudyInfoDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public void setSpecialFields(MedableStudy study) {
        this.setSponsor(study.getSponsor().getName());
        this.environment.setSpecialFields(study.getEnvironment());
    }
}
