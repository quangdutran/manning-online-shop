package com.dutran.outlet.service;

import com.dutran.outlet.dto.ItemDTO;

import java.util.List;

public interface ICatalogService {
    List<ItemDTO> getItemsForHomeSite();
    ItemDTO saveItem(ItemDTO item);
    ItemDTO getItemById(Integer id);
    List<ItemDTO> getItemsByIds(List<Integer> ids);
    ItemDTO getItemBySKU(String SKU);
}
