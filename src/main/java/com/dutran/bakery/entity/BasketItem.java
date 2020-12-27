package com.dutran.bakery.entity;

import com.dutran.bakery.dto.ItemDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BasketItem {
    final private ItemDTO item;
    final private int qty;

    BigDecimal getTotal() {
        return this.item.getPrice().multiply(BigDecimal.valueOf(qty));
    };
}
