package com.example.qcassistantmaven.domain.dto.language;

import com.google.gson.annotations.Expose;

public class LanguageTransferDTO {

    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public LanguageTransferDTO setName(String name) {
        this.name = name;
        return this;
    }
}
