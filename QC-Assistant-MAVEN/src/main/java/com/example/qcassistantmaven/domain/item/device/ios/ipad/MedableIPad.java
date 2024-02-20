package com.example.qcassistantmaven.domain.item.device.ios.ipad;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedableIPad {

    SIXTH_GEN("Medable\\s*Cellular\\s*Apple\\s*iPad\\s*6th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 6th Gen", ConnectorType.LIGHTNING),
    SEVENTH_GEN("Medable\\s*Cellular\\s*Apple\\s*iPad\\s*7th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 7th Gen", ConnectorType.LIGHTNING),
    EIGHT_GEN("Medable\\s*Cellular\\s*Apple\\s*iPad\\s*8th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 8th Gen", ConnectorType.LIGHTNING),
    NINTH_GEN("Medable\\s*.{0,16}\\s*Apple\\s*iPad\\s*9th.{0,32}(?<serial>[A-Z0-9]{10})",
            "iPad 9th Gen", ConnectorType.LIGHTNING),
    ;

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    MedableIPad(String regexPattern, String shortName, ConnectorType connectorType) {
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
