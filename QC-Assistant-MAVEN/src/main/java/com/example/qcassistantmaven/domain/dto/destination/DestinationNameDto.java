package com.example.qcassistantmaven.domain.dto.destination;

public class DestinationNameDto {

    private Long id;
    private String name;



    public Long getId() {
        return id;
    }

    public DestinationNameDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DestinationNameDto setName(String name) {
        this.name = name;
        return this;
    }
}
