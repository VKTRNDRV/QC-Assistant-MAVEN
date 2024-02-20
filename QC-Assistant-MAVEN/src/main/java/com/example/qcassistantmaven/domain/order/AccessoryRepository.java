package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.item.accessory.Accessory;

import java.util.ArrayList;
import java.util.Collection;

public class AccessoryRepository {

    private Collection<Accessory> accessories;

    public AccessoryRepository(){
        this.accessories = new ArrayList<>();
    }

    public void addAccessory(Accessory accessory){
        this.accessories.add(accessory);
    }

    public boolean containsAccessory(String shortName){
        for(Accessory accessory : accessories){
            if(accessory.getShortName().equals(shortName)){
                return true;
            }
        }

        return false;
    }

    public boolean containsAnyOfTheFollowing(Collection<String> itemNames) {
        for(String itemName : itemNames){
            if(this.containsAccessory(itemName)){
                return true;
            }
        }

        return false;
    }
}
