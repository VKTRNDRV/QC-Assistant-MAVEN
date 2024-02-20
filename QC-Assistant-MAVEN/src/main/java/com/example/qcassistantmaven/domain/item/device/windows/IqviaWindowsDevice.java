package com.example.qcassistantmaven.domain.item.device.windows;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.ShellType;

public enum IqviaWindowsDevice {

    SURFACE_PRO_7("IQVIA\\s*.{0,16}MS\\s*Surface\\s*Pro\\s*7\\s+.{0,32}(?<serial>[A-Z0-9]{12})",
            "Surface Pro 7", ConnectorType.TYPE_C, ShellType.TABLET),
    SURFACE_PRO_7_PLUS("IQVIA\\s*.{0,16}MS\\s*Surface\\s*Pro\\s*7\\+\\s+.{0,32}(?<serial>[A-Z0-9]{12})",
            "Surface Pro 7+", ConnectorType.TYPE_C, ShellType.TABLET),
    SURFACE_PRO_8("IQVIA\\s*.{0,16}MS\\s*Surface\\s*Pro\\s*8\\s+.{0,32}(?<serial>[A-Z0-9]{14})",
            "Surface Pro 8", ConnectorType.TYPE_C, ShellType.TABLET),
    SURFACE_PRO_9("IQVIA\\s*.{0,16}MS\\s*Surface\\s*Pro\\s*9\\s*.{0,32}(?<serial>[A-Z0-9]{14})",
            "Surface Pro 9", ConnectorType.TYPE_C, ShellType.TABLET),
    ;

    public static final String SERIAL_GROUP_NAME = "serial";
    private String regexPattern;
    private String shortName;
    private ConnectorType connectorType;
    private ShellType shellType;


    IqviaWindowsDevice(String regexPattern, String shortName, ConnectorType connectorType, ShellType shellType) {
        this.regexPattern = regexPattern;
        this.shortName = shortName;
        this.connectorType = connectorType;
        this.shellType = shellType;
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
    public ShellType getShellType() {
        return shellType;
    }
}
