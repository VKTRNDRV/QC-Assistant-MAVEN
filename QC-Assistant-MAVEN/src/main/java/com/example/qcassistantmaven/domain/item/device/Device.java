package com.example.qcassistantmaven.domain.item.device;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;

public interface Device {

    ConnectorType getConnectorType();

    OperatingSystem getOperatingSystem();

    ShellType getShellType();

    String getSerial();

    String getShortName();
}
