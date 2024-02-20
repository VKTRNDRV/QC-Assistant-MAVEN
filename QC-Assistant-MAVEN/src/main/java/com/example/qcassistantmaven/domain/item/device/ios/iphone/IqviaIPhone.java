package com.example.qcassistantmaven.domain.item.device.ios.iphone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum IqviaIPhone {

    IPHONE_7("IQVIA\\s*Apple\\s*iPhone\\s*7.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPhone 7", ConnectorType.LIGHTNING),
    IPHONE_8("IQVIA\\s*Apple\\s*iPhone\\s*8.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPhone 8", ConnectorType.LIGHTNING),
    IPHONE_SE_2ND_GEN("IQVIA\\s*Apple\\s*iPhone\\s*SE\\s*2nd.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPhone SE 2nd Gen", ConnectorType.LIGHTNING),
    IPHONE_SE_3RD_GEN("IQVIA\\s*Apple\\s*iPhone\\s*SE\\s*3rd.{0,32}(?<serial>[A-Z0-9]{10})",
            "iPhone SE 3rd Gen", ConnectorType.LIGHTNING),
    IPHONE_X("IQVIA\\s*Apple\\s*iPhone\\s*X\\s+.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPhone X", ConnectorType.LIGHTNING)
    ;

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    IqviaIPhone(String regexPattern, String shortName, ConnectorType connectorType) {
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

