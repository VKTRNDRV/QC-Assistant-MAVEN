package com.example.qcassistantmaven.domain.item.device.android;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.BaseDevice;

public class AndroidDevice extends BaseDevice {

    public AndroidDevice(String shortName, ConnectorType connectorType, ShellType shellType, String serial){
        super(shortName, OperatingSystem.ANDROID, connectorType, shellType, serial);
    }
}
