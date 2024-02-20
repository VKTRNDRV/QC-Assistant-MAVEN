package com.example.qcassistantmaven.domain.entity.sponsor;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseSponsor extends BaseEntity {

    @Column(nullable = false,
            unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "are_study_names_similar")
    private TrinaryBoolean areStudyNamesSimilar;

    public static final String UNKNOWN = "UNKNOWN";


    public String getName() {
        return name;
    }

    public BaseSponsor setName(String name) {
        this.name = name;
        return this;
    }

    public TrinaryBoolean getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public BaseSponsor setAreStudyNamesSimilar(TrinaryBoolean areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
