package com.example.qcassistantmaven.domain.item.device.ios.iphone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedidataIPhone {

    IPHONE_8("Medidata\\s*Apple\\s*iPhone\\s*8\\s*S.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone 8", ConnectorType.LIGHTNING),
    IPHONE_8_PLUS("Medidata\\s*Apple\\s*iPhone\\s*8\\s*P.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone 8 Plus", ConnectorType.LIGHTNING),
    IPHONE_SE("Medidata\\s*Apple\\s*iPhone\\s*[A0-9]{4}.{0,16}\\s*(?<serial>[A-Z0-9]{12})",
            "iPhone SE 2nd Gen", ConnectorType.LIGHTNING),
    IPHONE_SE_2ND_GEN("Medidata\\s*Apple\\s*iPhone\\s*SE.{0,16}\\s*2nd.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone SE 2nd Gen", ConnectorType.LIGHTNING),
    IPHONE_SE_3RD_GEN("Medidata\\s*Apple\\s*iPhone\\s*SE.{0,16}\\s*3rd.{0,16}(?<serial>[A-Z0-9]{10})",
            "iPhone SE 3rd Gen", ConnectorType.LIGHTNING),
    IPHONE_X("Medidata\\s*Apple\\s*iPhone\\s*X\\s.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone X", ConnectorType.LIGHTNING),
    IPHONE_XR("Medidata\\s*Apple\\s*iPhone\\s*XR.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone XR", ConnectorType.LIGHTNING),
    IPHONE_XS("Medidata\\s*Apple\\s*iPhone\\s*XS.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPhone XS", ConnectorType.LIGHTNING)
    ;

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    MedidataIPhone(String regexPattern, String shortName, ConnectorType connectorType) {
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
