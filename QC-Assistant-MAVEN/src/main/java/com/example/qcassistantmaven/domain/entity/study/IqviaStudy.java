package com.example.qcassistantmaven.domain.entity.study;

import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.*;

@Entity
@Table(name = "iqvia_studies")
public class IqviaStudy extends BaseStudy {

    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private IqviaSponsor sponsor;

    @OneToOne
    @JoinColumn(name = "environment_id")
    private IqviaEnvironment environment;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_translated_labels")
    private TrinaryBoolean containsTranslatedLabels;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_translated_docs")
    private TrinaryBoolean containsTranslatedDocs;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_sep_site_patient_labels")
    private TrinaryBoolean containsSepSitePatientLabels;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_gsg_plain")
    private TrinaryBoolean isGsgPlain;


    @Override
    public IqviaSponsor getSponsor() {
        return sponsor;
    }

    @Override
    public IqviaEnvironment getEnvironment() {
        return environment;
    }

    public IqviaStudy setSponsor(IqviaSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public IqviaStudy setEnvironment(IqviaEnvironment environment) {
        this.environment = environment;
        return this;
    }

    public TrinaryBoolean getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public IqviaStudy setContainsTranslatedLabels(TrinaryBoolean containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public TrinaryBoolean getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public IqviaStudy setContainsTranslatedDocs(TrinaryBoolean containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public TrinaryBoolean getContainsSepSitePatientLabels() {
        return containsSepSitePatientLabels;
    }

    public IqviaStudy setContainsSepSitePatientLabels(TrinaryBoolean containsSepSitePatientLabels) {
        this.containsSepSitePatientLabels = containsSepSitePatientLabels;
        return this;
    }

    public TrinaryBoolean getIsGsgPlain() {
        return isGsgPlain;
    }

    public IqviaStudy setIsGsgPlain(TrinaryBoolean isGsgPlain) {
        this.isGsgPlain = isGsgPlain;
        return this;
    }
}
