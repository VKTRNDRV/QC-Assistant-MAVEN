package com.example.qcassistantmaven.domain.dto.raw;

public class RawOrderInputDto {

    private String rawText;


    public String getRawText() {
        return rawText;
    }

    public RawOrderInputDto setRawText(String rawText) {
        this.rawText = rawText;
        return this;
    }

    public String getParsedRawText() {
        if (rawText == null) {
            return null;
        }

        return rawText
                .replace("\n", " ")
                .replace("\r", " ");
    }
}
