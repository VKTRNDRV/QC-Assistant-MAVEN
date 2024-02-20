package com.example.qcassistantmaven.domain.dto.study.transfer;

import com.example.qcassistantmaven.domain.dto.study.environment.transfer.BaseEnvironmentTransferDTO;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import com.google.gson.annotations.Expose;

public class IqviaStudyTransferDTO extends BaseStudyTransferDTO {

    @Expose
    private BaseEnvironmentTransferDTO environment;

    @Expose
    private TrinaryBoolean containsTranslatedLabels;

    @Expose
    private TrinaryBoolean containsTranslatedDocs;

    @Expose
    private TrinaryBoolean containsSepSitePatientLabels;

    @Expose
    private TrinaryBoolean isGsgPlain;



    public BaseEnvironmentTransferDTO getEnvironment() {
        return environment;
    }

    public IqviaStudyTransferDTO setEnvironment(BaseEnvironmentTransferDTO environment) {
        this.environment = environment;
        return this;
    }

    public TrinaryBoolean getIsGsgPlain() {
        return isGsgPlain;
    }

    public IqviaStudyTransferDTO setIsGsgPlain(TrinaryBoolean isGsgPlain) {
        this.isGsgPlain = isGsgPlain;
        return this;
    }

    public TrinaryBoolean getContainsSepSitePatientLabels() {
        return containsSepSitePatientLabels;
    }

    public IqviaStudyTransferDTO setContainsSepSitePatientLabels(TrinaryBoolean containsSepSitePatientLabels) {
        this.containsSepSitePatientLabels = containsSepSitePatientLabels;
        return this;
    }

    public TrinaryBoolean getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public IqviaStudyTransferDTO setContainsTranslatedDocs(TrinaryBoolean containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public TrinaryBoolean getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public IqviaStudyTransferDTO setContainsTranslatedLabels(TrinaryBoolean containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }
}
