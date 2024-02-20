package com.example.qcassistantmaven.domain.item.device.android.phone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum IqviaAndroidPhone {

    NOKIA_G20("IQVIA\\s*Nokia\\s*G20.{0,32}(?<serial>[A-Z0-9]{19})",
            "Nokia G20", ConnectorType.TYPE_C),
    MOTO_E7("IQVIA\\s*Motorola\\s*Moto\\s*E7.{0,48}(?<serial>[A-Z0-9]{10})",
            "Moto E7", ConnectorType.TYPE_C),
    GALAXY_J3("IQVIA\\s*Samsung\\s*Galaxy\\s*J3\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy J3", ConnectorType.MICRO_USB),
    A_10("IQVIA\\s*Samsung\\s*Galaxy\\s*A10\\s+.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A10", ConnectorType.MICRO_USB),
    A_10E("IQVIA\\s*Samsung\\s*Galaxy\\s*A10e\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A10e", ConnectorType.TYPE_C),
    A_11("IQVIA\\s*Samsung\\s*Galaxy\\s*A11\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A11", ConnectorType.TYPE_C),
    A_12("IQVIA\\s*Samsung\\s*A12\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A12", ConnectorType.TYPE_C),
    A_13("IQVIA\\s*Samsung\\s*.{0,16}A13\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A13", ConnectorType.TYPE_C),
    A_14("IQVIA\\s*Samsung\\s*.{0,16}A14\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Samsung A14", ConnectorType.TYPE_C),

    ;
    public static final String SERIAL_GROUP_NAME = "serial";
    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    IqviaAndroidPhone(String regexPattern, String shortName, ConnectorType connectorType) {
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
