package com.dutq.core.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ProductDTO {
    private String id;
    private boolean preOrder;
    private String name;
    private String description;
    private String metaDesc;
    private String keywords;
    private String link;
    private boolean isAvailable;
    private LocalDate dateAvailable;
    private ManufacturerDTO manufacturer;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;
    private boolean freeShipping;
    private Integer quantity;
    private Integer quantityOrderMax;
    private int quantityOrderMin;
    private BigDecimal price;
    private String publisher;
    private String formatType;
    private String country;
    private String style;
    private String mainPhotoLink;
    private List<String> photoLinks;
}
