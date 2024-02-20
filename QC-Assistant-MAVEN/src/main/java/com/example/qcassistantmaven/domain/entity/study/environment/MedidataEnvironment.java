package com.example.qcassistantmaven.domain.entity.study.environment;

import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medidata_environments")
public class MedidataEnvironment extends BaseEnvironment{

    @Enumerated(EnumType.STRING)
    @Column(name = "is_legacy")
    private TrinaryBoolean isLegacy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medidata_environments_patient_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<MedidataApp> patientApps;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medidata_environments_site_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<MedidataApp> siteApps;



    public TrinaryBoolean getIsLegacy() {
        return isLegacy;
    }

    public MedidataEnvironment setIsLegacy(TrinaryBoolean isLegacy) {
        this.isLegacy = isLegacy;
        return this;
    }

    @Override
    public List<MedidataApp> getPatientApps() {
        return patientApps;
    }

    public MedidataEnvironment setPatientApps(List<MedidataApp> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    @Override
    public List<MedidataApp> getSiteApps() {
        return siteApps;
    }

    public MedidataEnvironment setSiteApps(List<MedidataApp> siteApps) {
        this.siteApps = siteApps;
        return this;
    }
}
