package com.example.qcassistantmaven.domain.entity.destination;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "languages")
public class Language extends BaseEntity {

    @Column(nullable = false,
            unique = true)
    private String name;



    public static final String ENGLISH = "English";

    public String getName() {
        return name;
    }

    public Language setName(String name) {
        this.name = name;
        return this;
    }
}
