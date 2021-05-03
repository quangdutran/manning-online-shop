package com.dutq.core.model.request;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class CreateProductCategoryRequest {
    private String name;
    private String categoryCode;
    private String imageUrl;
    private BigInteger parentId;
    private String parentCode;
    private List<CreateProductCategoryRequest> childCategories;
}
