package com.example.qcassistantmaven.domain.item.device.ios.ipad;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedidataIPad {

    FIFTH_GEN("Medidata\\s*Apple\\s*iPad\\s*5th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 5th Gen", ConnectorType.LIGHTNING),

    SIXTH_GEN("Medidata\\s*Apple\\s*iPad\\s*6th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 6th Gen", ConnectorType.LIGHTNING),

    SEVENTH_GEN("Medidata\\s*Apple.{0,16}iPad\\s*7th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 7th Gen", ConnectorType.LIGHTNING),

    EIGHTH_GEN("Medidata.{0,16}Apple\\s*iPad\\s*8th.{0,32}(?<serial>[A-Z0-9]{12})",
            "iPad 8th Gen", ConnectorType.LIGHTNING),

    NINTH_GEN("Medidata\\s*Apple.{0,16}iPad\\s*9th.{0,32}(?<serial>[A-Z0-9]{10})",
            "iPad 9th Gen", ConnectorType.LIGHTNING),

    AIR_2("Medidata.{0,16}Apple\\s*iPad\\s*Air\\s*2.{0,24}(?<serial>[A-Z0-9]{12})",
            "iPad Air 2", ConnectorType.LIGHTNING),

    MINI("Medidata\\s*Apple\\s*iPad\\s*Mini.{0,16}(?<serial>[A-Z0-9]{12})",
            "iPad Mini", ConnectorType.LIGHTNING);

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    public static final String SERIAL_GROUP_NAME = "serial";

    MedidataIPad(String regexPattern, String shortName, ConnectorType connectorType) {
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
