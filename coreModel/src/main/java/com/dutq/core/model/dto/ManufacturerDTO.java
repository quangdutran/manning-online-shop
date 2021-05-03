package com.dutq.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManufacturerDTO {
    private BigInteger id;
    private String name;
    private String description;
    private String url;
    private String image;
}
