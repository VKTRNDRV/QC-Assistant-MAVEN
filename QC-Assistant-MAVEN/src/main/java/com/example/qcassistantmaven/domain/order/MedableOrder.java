package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;

public class MedableOrder extends ClinicalOrder{

    private MedableStudy study;

    private MedableSponsor sponsor;

    private SimRepository simRepository;


    @Override
    public boolean isStudyUnknown() {
        if(this.study == null){
            return true;
        }

        return this.study.isUnknown();
    }

    @Override
    public MedableStudy getStudy() {
        return study;
    }

    public MedableOrder setStudy(MedableStudy study) {
        this.study = study;
        return this;
    }

    public MedableSponsor getSponsor() {
        return sponsor;
    }

    public MedableOrder setSponsor(MedableSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public SimRepository getSimRepository() {
        return simRepository;
    }

    public MedableOrder setSimRepository(SimRepository simRepository) {
        this.simRepository = simRepository;
        return this;
    }
}
