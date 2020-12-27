package com.dutran.bakery.service;

import com.dutran.bakery.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findBySku(String sku);
}
