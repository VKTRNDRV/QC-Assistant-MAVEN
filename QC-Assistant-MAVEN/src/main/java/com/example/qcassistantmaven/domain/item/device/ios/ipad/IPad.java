package com.example.qcassistantmaven.domain.item.device.ios.ipad;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.ios.IOSDevice;

public class IPad extends IOSDevice {
    public IPad(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.TABLET, serial);
    }
}
