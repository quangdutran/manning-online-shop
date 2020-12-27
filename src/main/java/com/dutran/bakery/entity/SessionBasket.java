package com.dutran.bakery.entity;

import com.dutran.bakery.dto.ItemDTO;
import com.dutran.bakery.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class SessionBasket implements Basket {
    private final Map<String, BasketItem> items = new ConcurrentHashMap<>();
    @Autowired
    private ICatalogService catalogService;

    @Override
    public void add(String sku) {
        ItemDTO item = catalogService.getItemBySKU(sku);
        items.compute(sku, (existingSku, existingItem) -> {
            if (existingItem == null) {
                return new BasketItem(item, 1);
            }
            return new BasketItem(existingItem.getItem(), existingItem.getQty() + 1);
        });
    }

    @Override
    public int getTotalItems() {
        return items.values().stream().map(BasketItem::getQty).reduce(0, Integer::sum);
    }

    @Override
    public Collection<BasketItem> getItems() {
        return items.values();
    }

    @Override
    public void remove(String sku) {
        items.computeIfPresent(sku, (s, existingItem) -> {
            if (existingItem.getQty() == 1) {
                return null;
            }

            return new BasketItem(existingItem.getItem(), existingItem.getQty() - 1);
        });
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}
