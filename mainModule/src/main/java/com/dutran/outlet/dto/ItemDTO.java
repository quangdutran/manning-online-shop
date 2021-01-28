package com.dutran.outlet.dto;


import lombok.Builder;
import lombok.Data;

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
