package com.example.qcassistantmaven.domain.item.device.medical;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedableMedicalDevice {

    IWATCH_SERIES_5("Medable\\s*Apple\\s*iWatch\\s*Series\\s*5\\s*.{0,32}(?<serial>[A-Z0-9]{12})",
            "iWatch Series 5", ConnectorType.LIGHTNING),
    WATCH_SERIES_6("Medable\\s*Apple\\s*Watch\\s*Series\\s*6\\s*.{0,32}(?<serial>[A-Z0-9]{13})",
            "Apple Watch Series 6", ConnectorType.LIGHTNING),

    WATCH_SE("Medable\\s*Apple\\s*Watch\\s*SE\\s+.{0,48}(?<serial>[A-Z0-9]{12})",
            "Apple Watch SE", ConnectorType.LIGHTNING),
    WATCH_SE_2ND_GEN("Medable\\s*Apple\\s*Watch\\s*SE\\s*2nd.{0,32}(?<serial>[A-Z0-9]{11})",
            "Apple Watch SE 2nd Gen", ConnectorType.LIGHTNING),
    EVERION_TRACKER("Medable\\s*Everion\\s*Biovotion.{0,40}(?<serial>[A-Z0-9-]{14})",
            "Everion Tracker", ConnectorType.TYPE_C),
    MARSDEN_SCALE("Medable\\s*Marsden.{0,32}(?<serial>[A-Z0-9-]{8})",
            "Marsden Floor Scale", ConnectorType.OTHER),
    OXYMETER("Medable\\s*Masimo\\s*MightySat.{0,48}(?<serial>[0-9]{10})",
            "Pulse Oxymeter", ConnectorType.OTHER),
    BLOOD_PRESSURE_MONITOR("Medable\\s*Omron\\s*Blood.{0,32}(?<serial>[A-Z0-9]{13})",
            "Blood Pr. Monitor", ConnectorType.OTHER)

    ;
    public static final String SERIAL_GROUP_NAME = "serial";
    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    MedableMedicalDevice(String regexPattern, String shortName, ConnectorType connectorType) {
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
