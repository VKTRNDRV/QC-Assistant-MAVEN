package com.example.qcassistantmaven.domain.dto.item;

public class ItemNameSerialDto {

    private String shortName;
    private String serial;



    public String getShortName() {
        return shortName;
    }

    public ItemNameSerialDto setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getSerial() {
        return serial;
    }

    public ItemNameSerialDto setSerial(String serial) {
        this.serial = serial;
        return this;
    }
}
