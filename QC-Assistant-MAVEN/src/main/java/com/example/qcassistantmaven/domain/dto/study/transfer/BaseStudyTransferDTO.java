package com.example.qcassistantmaven.domain.dto.study.transfer;

import com.google.gson.annotations.Expose;

public class BaseStudyTransferDTO {

    @Expose
    private String sponsor;

    @Expose
    private String name;

    @Expose
    private String folderURL;


    public String getSponsor() {
        return sponsor;
    }

    public BaseStudyTransferDTO setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseStudyTransferDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public BaseStudyTransferDTO setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }
}
