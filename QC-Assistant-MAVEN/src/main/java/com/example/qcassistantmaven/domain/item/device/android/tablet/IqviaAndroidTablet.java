package com.example.qcassistantmaven.domain.item.device.android.tablet;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;

public enum IqviaAndroidTablet {

    GALAXY_TAB_A("IQVIA\\s*Samsung\\s*Galaxy\\s*Tab\\s*A\\s+.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy Tab A", ConnectorType.TYPE_C),
    GALAXY_TAB_A7("IQVIA\\s*Samsung\\s*Galaxy\\s*Tab\\s*A7\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy Tab A7", ConnectorType.TYPE_C),
    GALAXY_TAB_A8("IQVIA\\s*Samsung\\s*Galaxy\\s*Tab\\s*A8\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy Tab A8", ConnectorType.TYPE_C),
    GALAXY_TAB_S4("IQVIA\\s*Samsung\\s*Galaxy\\s*Tab\\s*S4\\s*.{0,32}(?<serial>[A-Z0-9]{11})",
            "Galaxy Tab S4", ConnectorType.TYPE_C)
    ;

    public static final String SERIAL_GROUP_NAME = "serial";
    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;

    IqviaAndroidTablet(String regexPattern, String shortName, ConnectorType connectorType) {
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
