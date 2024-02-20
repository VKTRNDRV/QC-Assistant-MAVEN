package com.example.qcassistantmaven.domain.item.device.windows;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.BaseDevice;

public class WindowsDevice extends BaseDevice {
    public WindowsDevice(String shortName, ConnectorType connectorType, ShellType shellType, String serial) {
        super(shortName, OperatingSystem.WINDOWS, connectorType, shellType, serial);
    }
}
