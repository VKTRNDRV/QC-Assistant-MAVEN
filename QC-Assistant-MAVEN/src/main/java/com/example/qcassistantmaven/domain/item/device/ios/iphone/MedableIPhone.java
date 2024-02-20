package com.example.qcassistantmaven.domain.item.device.ios.iphone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedableIPhone {

    IPHONE_8("Medable\\s*Apple\\s*iPhone\\s*8.{0,16}\\s*(?<serial>[A-Z0-9]{12})",
            "iPhone 8", ConnectorType.LIGHTNING),
    IPHONE_X("Medable\\s*Apple\\s*iPhone\\s*X\\s+.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPhone X", ConnectorType.LIGHTNING),
    IPHONE_XR("Medable\\s*Apple\\s*iPhone\\s*XR\\s*.{0,16}\\s*Shell\\s*(?<serial>[A-Z0-9]{12})",
            "iPhone XR", ConnectorType.LIGHTNING),
    IPHONE_11("Medable\\s*Apple\\s*iPhone\\s*XR\\s*.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone 11", ConnectorType.LIGHTNING),
    IPHONE_SE_2ND_GEN("Medable\\s*Apple\\s*iPhone\\s*SE\\s*2nd.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone SE 2nd Gen", ConnectorType.LIGHTNING),
    IPHONE_SE_3RD_GEN("Medable\\s*Apple\\s*iPhone\\s*SE\\s*3rd\\s*Gen\\s*.{0,16}\\s*Shell\\s*(?<serial>[A-Z0-9]{10})",
            "iPhone SE 3rd Gen", ConnectorType.LIGHTNING)
    ;

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    MedableIPhone(String regexPattern, String shortName, ConnectorType connectorType) {
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
