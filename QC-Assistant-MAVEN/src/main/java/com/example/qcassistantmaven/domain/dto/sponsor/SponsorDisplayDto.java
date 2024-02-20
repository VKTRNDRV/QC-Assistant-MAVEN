package com.example.qcassistantmaven.domain.dto.sponsor;

public class SponsorDisplayDto {

    private Long id;

    private String name;

    private String areStudyNamesSimilar;

    public Long getId() {
        return id;
    }

    public SponsorDisplayDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SponsorDisplayDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getAreStudyNamesSimilar() {
        return areStudyNamesSimilar;
    }

    public SponsorDisplayDto setAreStudyNamesSimilar(String areStudyNamesSimilar) {
        this.areStudyNamesSimilar = areStudyNamesSimilar;
        return this;
    }
}
