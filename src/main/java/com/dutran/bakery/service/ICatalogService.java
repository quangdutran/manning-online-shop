package com.dutran.bakery.service;

import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.entity.Item;

import java.util.List;

public interface ICatalogService {
    List<ItemDTO> getItemsForHomeSite();
}
