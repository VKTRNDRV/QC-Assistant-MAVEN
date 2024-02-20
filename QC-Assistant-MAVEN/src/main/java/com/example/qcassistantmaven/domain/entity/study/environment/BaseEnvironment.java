package com.example.qcassistantmaven.domain.entity.study.environment;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

import java.util.List;

@MappedSuperclass
public abstract class BaseEnvironment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "is_site_patient_separated")
    private TrinaryBoolean isSitePatientSeparated;


    @Enumerated(EnumType.STRING)
    @Column(name = "is_destination_separated")
    private TrinaryBoolean isDestinationSeparated;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_os_separated")
    private TrinaryBoolean isOsSeparated;


    public TrinaryBoolean getIsSitePatientSeparated() {
        return isSitePatientSeparated;
    }

    public BaseEnvironment setIsSitePatientSeparated(TrinaryBoolean isSitePatientSeparated) {
        this.isSitePatientSeparated = isSitePatientSeparated;
        return this;
    }

    public TrinaryBoolean getIsDestinationSeparated() {
        return isDestinationSeparated;
    }

    public BaseEnvironment setIsDestinationSeparated(TrinaryBoolean isDestinationSeparated) {
        this.isDestinationSeparated = isDestinationSeparated;
        return this;
    }

    public TrinaryBoolean getIsOsSeparated() {
        return isOsSeparated;
    }

    public BaseEnvironment setIsOsSeparated(TrinaryBoolean isOsSeparated) {
        this.isOsSeparated = isOsSeparated;
        return this;
    }

    public abstract List<? extends BaseApp> getPatientApps();

    public abstract List<? extends BaseApp> getSiteApps();
}
