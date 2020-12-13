package com.dutran.bakery.service;

import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.repository.ItemRepository;
import com.dutran.bakery.utility.NumberFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatalogServiceImpl implements ICatalogService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<ItemDTO> getItemsForHomeSite() {
        return itemRepository.findAll()
                .stream()
                .map(item -> ItemDTO.builder()
                .description(item.getDescription())
                .name(item.getName())
                .price(NumberFormatUtil.formatPrice(item.getPrice()))
                .sku(item.getSku())
                .build())
                .collect(Collectors.toList());
    }
}
