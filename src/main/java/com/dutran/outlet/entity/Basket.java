package com.dutran.outlet.entity;

import java.util.Collection;

public interface Basket {
    void add(String sku);

    int getTotalItems();

    Collection<BasketItem> getItems();

    void remove(String sku);

    void clear();
}
