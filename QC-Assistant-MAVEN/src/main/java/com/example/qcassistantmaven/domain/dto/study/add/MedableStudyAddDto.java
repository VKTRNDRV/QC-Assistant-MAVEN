package com.example.qcassistantmaven.domain.dto.study.add;

import com.example.qcassistantmaven.domain.dto.study.environment.add.MedableEnvironmentAddDto;

public class MedableStudyAddDto {

    private String name;
    private String folderURL;
    private String sponsor;
    private MedableEnvironmentAddDto environment;



    public String getName() {
        return name;
    }

    public MedableStudyAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public MedableStudyAddDto setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public MedableStudyAddDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public MedableEnvironmentAddDto getEnvironment() {
        return environment;
    }

    public MedableStudyAddDto setEnvironment(MedableEnvironmentAddDto environment) {
        this.environment = environment;
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
