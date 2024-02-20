package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;

public class IqviaOrder extends ClinicalOrder{

    private IqviaStudy study;

    private IqviaSponsor sponsor;

    private SimRepository simRepository;

    private DocumentRepository documentRepository;

    @Override
    public boolean isStudyUnknown() {
        if(this.study == null){
            return true;
        }

        return this.study.isUnknown();
    }

    @Override
    public IqviaStudy getStudy() {
        return study;
    }

    public IqviaOrder setStudy(IqviaStudy study) {
        this.study = study;
        return this;
    }

    public IqviaSponsor getSponsor() {
        return sponsor;
    }

    public IqviaOrder setSponsor(IqviaSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public SimRepository getSimRepository() {
        return simRepository;
    }

    public IqviaOrder setSimRepository(SimRepository simRepository) {
        this.simRepository = simRepository;
        return this;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public IqviaOrder setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        return this;
    }
}
