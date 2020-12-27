package com.dutran.bakery.service.mapper;

import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.entity.Item;

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
