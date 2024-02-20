package com.example.qcassistantmaven.domain.dto.sponsor;

public class SponsorEditDto {

    private Long id;
    private String name;
    private String areStudyNamesSimilar;


    public Long getId() {
        return id;
    }

    public SponsorEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SponsorEditDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public SponsorEditDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }

    public void trimStringFields() {
        if(this.name == null){
            return;
        }

        this.name = this.name.trim();
    }
}
