package com.example.qcassistantmaven.domain.item.accessory;

import com.example.qcassistantmaven.domain.enums.item.ItemType;
import com.example.qcassistantmaven.domain.item.BaseItem;

public class Accessory extends BaseItem {
    public Accessory(String name) {
        super(name, ItemType.ACCESSORY);
    }
}
