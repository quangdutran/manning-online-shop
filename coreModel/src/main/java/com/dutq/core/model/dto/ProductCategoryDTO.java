package com.dutq.core.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ProductCategoryDTO {
    private BigInteger id;
    private String name;
    private String imageUrl;
    private BigInteger parentId;
    private List<ProductCategoryDTO> children;
}
