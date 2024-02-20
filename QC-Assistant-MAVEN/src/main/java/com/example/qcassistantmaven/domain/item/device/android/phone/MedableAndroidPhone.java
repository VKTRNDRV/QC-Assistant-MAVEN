package com.example.qcassistantmaven.domain.item.device.android.phone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedableAndroidPhone {

    MOTO_E32S("Medable\\s*Motorola\\s*Moto\\s*e32\\s*.{0,40}(?<serial>[A-Z0-9]{10})",
            "Moto e32s", ConnectorType.TYPE_C),
    MOTO_E6("Medable\\s*Motorola\\s*Moto\\s*E6\\s*.{0,16}SmartPhone\\s*Shell\\s*(?<serial>[A-Z0-9]{10})",
            "Moto E6", ConnectorType.MICRO_USB),
    MOTO_E7("Medable\\s*Motorola\\s*Moto\\s*E7\\s*.{0,32}(?<serial>[A-Z0-9]{10})",
            "Moto E7", ConnectorType.TYPE_C),
    MOTO_G_PRO("Medable\\s*Motorola\\s*Moto\\s*G\\s*Pro.{0,40}(?<serial>[A-Z0-9]{10})",
            "Moto G Pro", ConnectorType.TYPE_C),
    MOTO_G_STYLUS("Medable\\s*Motorola\\s*Moto\\s*[gG]{1}\\s*Stylus.{0,32}(?<serial>[A-Z0-9]{10})",
            "Moto G Stylus", ConnectorType.TYPE_C),
    MOTO_G22("Medable\\s*Motorola\\s*Moto\\s*g2.{0,32}(?<serial>[A-Z0-9]{11})",
            "Moto G22", ConnectorType.TYPE_C),
    MOTO_G6("Medable\\s*Motorola\\s*Moto\\s*G6.{0,40}(?<serial>[A-Z0-9]{10})",
            "Moto G6", ConnectorType.TYPE_C),
    MOTO_G7("Medable\\s*Motorola\\s*Moto\\s*G7.{0,40}(?<serial>[A-Z0-9]{10})",
            "Moto G7", ConnectorType.TYPE_C),
    A_11("Medable\\s*Samsung\\s*Galaxy\\s*A11\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A11", ConnectorType.TYPE_C),
    A_12("Medable\\s*Samsung\\s*.{0,16}A12\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A12", ConnectorType.TYPE_C),
    A_13("Medable\\s*Samsung\\s*.{0,16}A13\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A13", ConnectorType.TYPE_C),
    A_14("Medable\\s*Samsung.{0,16}A14.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A14", ConnectorType.TYPE_C),
    S_10("Medable\\s*Samsung\\s*Galaxy\\s*S10\\s+.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung S10", ConnectorType.TYPE_C),
    S_10E("Medable\\s*Samsung\\s*Galaxy\\s*S10e\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung S10e", ConnectorType.TYPE_C),

    ;
    public static final String SERIAL_GROUP_NAME = "serial";
    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    MedableAndroidPhone(String regexPattern, String shortName, ConnectorType connectorType) {
        this.regexPattern = regexPattern;
        this.shortName = shortName;
        this.connectorType = connectorType;
    }


    public String getRegexPattern() {
        return regexPattern;
    }

    public String getShortName() {
        return shortName;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }
}
