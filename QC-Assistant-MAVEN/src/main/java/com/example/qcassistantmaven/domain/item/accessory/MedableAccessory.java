package com.example.qcassistantmaven.domain.item.accessory;

public enum MedableAccessory {

    MOTOROLA_HEADPHONES("Medable Motorola Headphones", "Motorola Headphones"),
    SCREEN_PROTECTOR("Screen Protector", "Screen Protector"),
    ;

    private String regexPattern;

    private String shortName;

    MedableAccessory(String regexPattern, String shortName){
        this.regexPattern = regexPattern;
        this.shortName = shortName;
    }

    public String getRegexPattern() {
        return regexPattern;
    }

    public String getShortName() {
        return shortName;
    }
}
