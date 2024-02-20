package com.example.qcassistantmaven.domain.entity.study;

import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.*;

@Entity
@Table(name = "medidata_studies")
public class MedidataStudy extends BaseStudy{

    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private MedidataSponsor sponsor;

    @OneToOne
    @JoinColumn(name = "environment_id")
    private MedidataEnvironment environment;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_translated_labels")
    private TrinaryBoolean containsTranslatedLabels;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_translated_docs")
    private TrinaryBoolean containsTranslatedDocs;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_editable_welcome_letter")
    private TrinaryBoolean containsEditableWelcomeLetter;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_patient_device_ipad")
    private TrinaryBoolean isPatientDeviceIpad;

    @Enumerated(EnumType.STRING)
    @Column(name = "includes_headphones_styluses")
    private TrinaryBoolean includesHeadphonesStyluses;


    @Override
    public MedidataSponsor getSponsor() {
        return sponsor;
    }

    public MedidataStudy setSponsor(MedidataSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    @Override
    public MedidataEnvironment getEnvironment() {
        return environment;
    }

    public MedidataStudy setEnvironment(MedidataEnvironment environment) {
        this.environment = environment;
        return this;
    }

    public TrinaryBoolean getContainsTranslatedLabels() {
        return containsTranslatedLabels;
    }

    public MedidataStudy setContainsTranslatedLabels(TrinaryBoolean containsTranslatedLabels) {
        this.containsTranslatedLabels = containsTranslatedLabels;
        return this;
    }

    public TrinaryBoolean getContainsTranslatedDocs() {
        return containsTranslatedDocs;
    }

    public MedidataStudy setContainsTranslatedDocs(TrinaryBoolean containsTranslatedDocs) {
        this.containsTranslatedDocs = containsTranslatedDocs;
        return this;
    }

    public TrinaryBoolean getContainsEditableWelcomeLetter() {
        return containsEditableWelcomeLetter;
    }

    public MedidataStudy setContainsEditableWelcomeLetter(TrinaryBoolean containsEditableWelcomeLetter) {
        this.containsEditableWelcomeLetter = containsEditableWelcomeLetter;
        return this;
    }

    public TrinaryBoolean getIsPatientDeviceIpad() {
        return isPatientDeviceIpad;
    }

    public MedidataStudy setIsPatientDeviceIpad(TrinaryBoolean isPatientDeviceIpad) {
        this.isPatientDeviceIpad = isPatientDeviceIpad;
        return this;
    }

    public TrinaryBoolean getIncludesHeadphonesStyluses() {
        return includesHeadphonesStyluses;
    }

    public MedidataStudy setIncludesHeadphonesStyluses(TrinaryBoolean includesHeadphonesStyluses) {
        this.includesHeadphonesStyluses = includesHeadphonesStyluses;
        return this;
    }

    public boolean containsSiteApp(String appName) {
        for(MedidataApp app : environment.getSiteApps()){
            if(app.getName().equals(appName)){
                return true;
            }
        }

        return false;
    }

    public boolean containsPatientApp(String appName) {
        for(MedidataApp app : environment.getPatientApps()){
            if(app.getName().equals(appName)){
                return true;
            }
        }

        return false;
    }
}
