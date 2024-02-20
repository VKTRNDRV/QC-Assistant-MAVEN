package com.example.qcassistantmaven.domain.entity.destination;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.enums.item.PlugType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination extends BaseEntity {

    @Column(nullable = false,
            unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "destinations_languages",
            joinColumns = { @JoinColumn(name = "destination_id") },
            inverseJoinColumns = { @JoinColumn(name = "language_id") }
    )
    private List<Language> languages;

    @Enumerated(EnumType.STRING)
    @Column(name = "plug_type")
    private PlugType plugType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sim_type")
    private SimType simType;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_special_model")
    private TrinaryBoolean requiresSpecialModels;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_unused_devices")
    private TrinaryBoolean requiresUnusedDevices;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_invoice")
    private TrinaryBoolean requiresInvoice;

    public static final String EGYPT = "Egypt";
    public static final String ISRAEL = "Israel";
    public static final String TURKEY = "Turkey";
    public static final String HONG_KONG = "Hong Kong";
    public static final String CHINA = "China";


    public String getName() {
        return name;
    }

    public Destination setName(String name) {
        this.name = name;
        return this;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public Destination setLanguages(List<Language> languages) {
        this.languages = languages;
        return this;
    }

    public PlugType getPlugType() {
        return plugType;
    }

    public Destination setPlugType(PlugType plugType) {
        this.plugType = plugType;
        return this;
    }

    public SimType getSimType() {
        return simType;
    }

    public Destination setSimType(SimType simType) {
        this.simType = simType;
        return this;
    }

    public TrinaryBoolean getRequiresSpecialModels() {
        return requiresSpecialModels;
    }

    public Destination setRequiresSpecialModels(TrinaryBoolean requiresSpecialModels) {
        this.requiresSpecialModels = requiresSpecialModels;
        return this;
    }

    public TrinaryBoolean getRequiresUnusedDevices() {
        return requiresUnusedDevices;
    }

    public Destination setRequiresUnusedDevices(TrinaryBoolean requiresUnusedDevices) {
        this.requiresUnusedDevices = requiresUnusedDevices;
        return this;
    }

    public TrinaryBoolean getRequiresInvoice() {
        return requiresInvoice;
    }

    public Destination setRequiresInvoice(TrinaryBoolean requiresInvoice) {
        this.requiresInvoice = requiresInvoice;
        return this;
    }

    public boolean isUnknown() {
        return this.name.equals(BaseEntity.UNKNOWN);
    }

    public boolean isEnglishSpeaking() {
        if(this.isUnknown()){
            return false;
        }
        for(Language language : languages){
            if(language.getName().equals(Language.ENGLISH)){
                return true;
            }
        }

        return false;
    }

    public boolean containsLanguage(Language language) {
        for(Language destLanguages : languages){
            if(destLanguages.getName().equals(language.getName())){
                return true;
            }
        }

        return false;
    }
}
