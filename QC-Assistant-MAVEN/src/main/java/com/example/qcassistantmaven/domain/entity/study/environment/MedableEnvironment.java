package com.example.qcassistantmaven.domain.entity.study.environment;

import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.domain.entity.app.MedableApp;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medable_environments")
public class MedableEnvironment extends BaseEnvironment{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medable_environments_patient_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<MedableApp> patientApps;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "medable_environments_site_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<MedableApp> siteApps;

    @Enumerated(EnumType.STRING)
    @Column(name = "contains_china_group")
    private TrinaryBoolean containsChinaGroup;





    @Override
    public List<MedableApp> getPatientApps() {
        return patientApps;
    }

    public MedableEnvironment setPatientApps(List<MedableApp> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    @Override
    public List<MedableApp> getSiteApps() {
        return siteApps;
    }

    public MedableEnvironment setSiteApps(List<MedableApp> siteApps) {
        this.siteApps = siteApps;
        return this;
    }

    public TrinaryBoolean getContainsChinaGroup() {
        return containsChinaGroup;
    }

    public MedableEnvironment setContainsChinaGroup(TrinaryBoolean containsChinaGroup) {
        this.containsChinaGroup = containsChinaGroup;
        return this;
    }
}
