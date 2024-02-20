package com.example.qcassistantmaven.domain.item.device.android.phone;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.android.AndroidDevice;

public class AndroidPhone extends AndroidDevice {
    public AndroidPhone(String shortName, ConnectorType connectorType, String serial) {
        super(shortName, connectorType, ShellType.PHONE, serial);
    }
}
