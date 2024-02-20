package com.example.qcassistantmaven.domain.item.device.ios.iphone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.ios.IOSDevice;

public class IPhone extends IOSDevice {
    public IPhone(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.PHONE, serial);
    }
}
