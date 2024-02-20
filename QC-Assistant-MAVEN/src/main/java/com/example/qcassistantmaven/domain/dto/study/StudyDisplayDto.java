package com.example.qcassistantmaven.domain.dto.study;

public class StudyDisplayDto {

    private Long id;

    private String sponsor;

    private String name;


    public Long getId() {
        return id;
    }

    public StudyDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public StudyDisplayDto setSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudyDisplayDto setName(String name) {
        this.name = name;
        return this;
    }

    public String displayName(){
        return sponsor + " // " + name;
    }
}
