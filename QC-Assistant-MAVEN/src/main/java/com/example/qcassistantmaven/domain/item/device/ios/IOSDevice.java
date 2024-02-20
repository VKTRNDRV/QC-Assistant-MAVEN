package com.example.qcassistantmaven.domain.item.device.ios;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.BaseDevice;

public class IOSDevice extends BaseDevice {

    public IOSDevice(String shortName, ConnectorType connectorType, ShellType shellType, String serial){
        super(shortName, OperatingSystem.IOS, connectorType, shellType, serial);
    }
}
