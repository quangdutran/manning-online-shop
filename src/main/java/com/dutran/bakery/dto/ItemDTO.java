package com.dutran.bakery.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDTO {
    private Integer id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
}
