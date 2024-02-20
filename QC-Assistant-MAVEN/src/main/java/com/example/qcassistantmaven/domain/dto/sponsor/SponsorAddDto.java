package com.example.qcassistantmaven.domain.dto.sponsor;

public class SponsorAddDto {

    private String name;
    private String areStudyNamesSimilar;


    public String getName() {
        return name;
    }

    public SponsorAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public SponsorAddDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
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
