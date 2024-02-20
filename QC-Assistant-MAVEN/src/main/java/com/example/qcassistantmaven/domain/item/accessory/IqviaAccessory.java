package com.example.qcassistantmaven.domain.item.accessory;

public enum IqviaAccessory {

    IPAD_STYLUS("IQVIA Universal iPad Stylus\\/Ball", "iPad Stylus"),
    TABLET_STYLUS("IQVIA Universal Tablet Stylus", "Tablet Stylus"),
    APPLE_HEADPHONES("IQVIA Apple iPhone Lightning", "Apple Headphones"),
    SAMSUNG_HEADPHONES("IQVIA Samsung Galaxy Headphones", "Samsung Headphones"),
    MOTOROLA_HEADPHONES("IQVIA Motorola Headphones", "Motorola Headphones"),
    TABLET_STAND("IQVIA Universal Desktop", "Tablet Stand"),
    PHONE_STAND("IQVIA Universal Folding", "Phone Stand")
    ;

    private String regexPattern;

    private String shortName;

    IqviaAccessory(String regexPattern, String shortName){
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
