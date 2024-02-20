package com.example.qcassistantmaven.domain.item.device.android.phone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum MedidataAndroidPhone {

    GALAXY_S7("Medidata\\s*Samsung\\s*Galaxy\\s*S7.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy S7",
            ConnectorType.MICRO_USB),

    GALAXY_J2("Medidata\\s*Samsung\\s*Galaxy\\s*J2.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy J2",
            ConnectorType.MICRO_USB),
    GALAXY_J3("Medidata\\s*Samsung\\s*Galaxy\\s*J3\\s2.{0,40}(?<serial>[A-Z0-9]{11})",
            "Galaxy J3",
            ConnectorType.MICRO_USB),
    GALAXY_J3_STAR("Medidata\\s*Samsung\\s*Galaxy\\s*J3\\sStar.{0,40}(?<serial>[A-Z0-9]{11})",
            "Galaxy J3 Star",
            ConnectorType.MICRO_USB)
    ;

    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    MedidataAndroidPhone(String regexPattern, String shortName, ConnectorType connectorType) {
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
