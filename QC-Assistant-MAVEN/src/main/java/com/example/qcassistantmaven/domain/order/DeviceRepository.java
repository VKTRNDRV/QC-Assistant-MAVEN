package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.Device;
import org.springframework.security.core.parameters.P;

import java.util.*;

public class DeviceRepository {

    private List<Device> devices;

    public DeviceRepository(){
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device){
        this.devices.add(device);
    }

    public Collection<Device> getDevices(){
        return Collections.unmodifiableCollection(devices);
    }

    public boolean containsAndroidDevices() {
        return this.containsOperatingSystem(
                OperatingSystem.ANDROID);
    }

    public boolean containsShellType(ShellType shellType){
        for (Device device : devices){
            if(device.getShellType().equals(shellType)){
                return true;
            }
        }

        return false;
    }

    public boolean containsDevice(String shortName){
        for(Device device : devices){
            if(device.getShortName().equals(shortName)){
                return true;
            }
        }

        return false;
    }

    public int count(){
        return devices.size();
    }

    public boolean containsIosDevices() {
        return containsOperatingSystem(OperatingSystem.IOS);
    }

    public boolean containsWindowsDevices() {
        return containsOperatingSystem(OperatingSystem.WINDOWS);
    }

    public Optional<List<String>> getDuplicateSerials() {
        List<String> duplicates = new ArrayList<>();
        for (int i = 0; i < this.devices.size() - 1; i++) {
            String testSerial = this.devices.get(i).getSerial();

            for (int j = i + 1; j < this.devices.size(); j++) {
                String currentSerial = this.devices.get(j).getSerial();

                if(testSerial.equals(currentSerial)){
                    duplicates.add(testSerial);
                }
            }
        }

        if(duplicates.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(duplicates);
        }
    }

    public boolean containsDvcsWithConnector(ConnectorType connectorType) {
        for(Device device : devices){
            if (device.getConnectorType().equals(connectorType)){
                return true;
            }
        }

        return false;
    }

    public boolean containsAnyOfTheFollowing(Collection<String> deviceNames) {
        for(String shortName : deviceNames){
            if(this.containsDevice(shortName)){
                return true;
            }
        }

        return false;
    }

    public boolean containsDevicesOfOsAndShell(OperatingSystem operatingSystem, ShellType shellType) {
        for (Device device : devices){
            if(device.getOperatingSystem().equals(operatingSystem) &&
                    device.getShellType().equals(shellType)){
                return true;
            }
        }

        return false;
    }

    public boolean containsOperatingSystem(OperatingSystem operatingSystem) {
        for(Device device : devices){
            if(device.getOperatingSystem().equals(operatingSystem)){
                return true;
            }
        }

        return false;
    }
}
