package com.dutran.outlet.service.mapper;

import com.dutran.outlet.dto.ItemDTO;
import com.dutran.outlet.entity.Item;

public class Mapper {
    public static Item itemDTOToItem(ItemDTO item) {
        Item returnedItem = new Item();
        returnedItem.setName(item.getName());
        returnedItem.setSku(item.getSku());
        returnedItem.setDescription(item.getDescription());
        returnedItem.setPrice(item.getPrice());
        return returnedItem;
    }

}
