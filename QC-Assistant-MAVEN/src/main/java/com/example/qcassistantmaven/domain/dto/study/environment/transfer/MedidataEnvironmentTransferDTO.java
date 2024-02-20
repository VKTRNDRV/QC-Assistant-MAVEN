package com.example.qcassistantmaven.domain.dto.study.environment.transfer;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MedidataEnvironmentTransferDTO extends BaseEnvironmentTransferDTO{

    @Expose
    private String isLegacy;

    public String getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironmentTransferDTO setIsLegacy(String isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }
}
