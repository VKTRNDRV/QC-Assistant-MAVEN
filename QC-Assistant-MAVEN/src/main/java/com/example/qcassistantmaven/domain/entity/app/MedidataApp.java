package com.example.qcassistantmaven.domain.entity.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "medidata_apps")
public class MedidataApp extends BaseApp{

    public static final String PATIENT_CLOUD = "Patient Cloud";
    public static final String RAVE_ECONSENT = "Rave eConsent";;
}
