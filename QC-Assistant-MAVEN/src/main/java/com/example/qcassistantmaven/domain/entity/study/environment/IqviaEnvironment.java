package com.example.qcassistantmaven.domain.entity.study.environment;

import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "iqvia_environments")
public class IqviaEnvironment extends BaseEnvironment{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "iqvia_environments_patient_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<IqviaApp> patientApps;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "iqvia_environments_site_apps",
            joinColumns = { @JoinColumn(name = "environment_id") },
            inverseJoinColumns = { @JoinColumn(name = "app_id") }
    )
    private List<IqviaApp> siteApps;





    @Override
    public List<IqviaApp> getPatientApps() {
        return patientApps;
    }

    public IqviaEnvironment setPatientApps(List<IqviaApp> patientApps) {
        this.patientApps = patientApps;
        return this;
    }

    @Override
    public List<IqviaApp> getSiteApps() {
        return siteApps;
    }


    public IqviaEnvironment setSiteApps(List<IqviaApp> siteApps) {
        this.siteApps = siteApps;
        return this;
    }
}
