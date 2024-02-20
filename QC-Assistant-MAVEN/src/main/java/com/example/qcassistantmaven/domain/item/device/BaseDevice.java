package com.example.qcassistantmaven.domain.item.device;

import com.example.qcassistantmaven.domain.enums.item.ItemType;
import com.example.qcassistantmaven.domain.item.BaseItem;
import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;

public abstract class BaseDevice extends BaseItem implements Device{

    private OperatingSystem operatingSystem;
    private ConnectorType connectorType;
    private ShellType shellType;
    private String serial;


    public BaseDevice(String shortName, OperatingSystem operatingSystem,
                      ConnectorType connectorType, ShellType shellType, String serial){
        super(shortName, ItemType.DEVICE);
        setOperatingSystem(operatingSystem);
        setConnectorType(connectorType);
        setShellType(shellType);
        setSerial(serial);
    }

    @Override
    public String getShortName(){
        return super.getShortName();
    }


    @Override
    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public BaseDevice setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    @Override
    public ConnectorType getConnectorType() {
        return connectorType;
    }


    public BaseDevice setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
        return this;
    }

    @Override
    public ShellType getShellType() {
        return shellType;
    }


    public BaseDevice setShellType(ShellType shellType) {
        this.shellType = shellType;
        return this;
    }

    @Override
    public String getSerial() {
        return serial;
    }

    public BaseDevice setSerial(String serial) {
        this.serial = serial;
        return this;
    }
}
