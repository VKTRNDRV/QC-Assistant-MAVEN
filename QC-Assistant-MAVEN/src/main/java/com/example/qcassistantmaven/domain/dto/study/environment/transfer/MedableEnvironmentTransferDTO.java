package com.example.qcassistantmaven.domain.dto.study.environment.transfer;


import com.google.gson.annotations.Expose;

public class MedableEnvironmentTransferDTO extends BaseEnvironmentTransferDTO{

    @Expose
    private String containsChinaGroup;



    public String getContainsChinaGroup() {
        return containsChinaGroup;
    }

    public MedableEnvironmentTransferDTO setContainsChinaGroup(String containsChinaGroup) {
        this.containsChinaGroup = containsChinaGroup;
        return this;
    }
}
