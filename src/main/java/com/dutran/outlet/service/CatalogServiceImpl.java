package com.dutran.outlet.service;

import com.dutran.outlet.config.AppConfig;
import com.dutran.outlet.dto.ItemDTO;
import com.dutran.outlet.entity.Item;
import com.dutran.outlet.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements ICatalogService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AppConfig appConfig;

    @Override
    @Transactional
    public List<ItemDTO> getItemsForHomeSite() {
        return itemRepository.findAll()
                .stream()
                .map(item -> ItemDTO.builder()
                        .description(item.getDescription())
                        .name(item.getName())
                        .price(item.getPrice())
                        .sku(item.getSku())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ItemDTO saveItem(ItemDTO item) {
        Item createdItem = itemRepository.save(Mapper.itemDTOToItem(item));
        item.setId(createdItem.getId());
        return item;
    }

    @Override
    public ItemDTO getItemById(Integer id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(i ->
                ItemDTO.builder().id(i.getId())
                        .description(i.getDescription())
                        .name(i.getName())
                        .sku(i.getSku())
                        .price(i.getPrice())
                        .build())
                .orElse(ItemDTO.builder().build());
    }

    @Override
    public List<ItemDTO> getItemsByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public ItemDTO getItemBySKU(String SKU) {
        Item item = itemRepository.findBySku(SKU);
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .sku(item.getSku())
                .build();
    }


}
