package com.example.qcassistantmaven.domain.dto.language;

public class LanguageDto {

    private Long id;
    private String name;


    public Long getId() {
        return id;
    }

    public LanguageDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LanguageDto setName(String name) {
        this.name = name;
        return this;
    }
}
