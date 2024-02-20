package com.example.qcassistantmaven.domain.entity.study;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistantmaven.domain.entity.study.environment.BaseEnvironment;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseStudy extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column(name = "folder_url",
            columnDefinition = "TEXT")
    private String folderURL;

    public String getName() {
        return name;
    }

    public BaseStudy setName(String name) {
        this.name = name;
        return this;
    }

    public String getFolderURL() {
        return folderURL;
    }

    public BaseStudy setFolderURL(String folderURL) {
        this.folderURL = folderURL;
        return this;
    }

    public boolean isUnknown() {
        return name.equals(UNKNOWN);
    }

    public abstract <T extends BaseSponsor> T getSponsor();

    public abstract <T extends BaseEnvironment> T getEnvironment();
}
