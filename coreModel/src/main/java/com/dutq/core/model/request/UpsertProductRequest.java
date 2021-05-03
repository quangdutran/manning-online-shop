package com.dutq.core.model.request;

import com.dutq.core.model.dto.ManufacturerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UpsertProductRequest {
    private ManufacturerDTO manufacturer;
    private boolean preOrder;
    private String name;
    private String description;
    private String metaDesc;
    private String keywords;
    private String link;
    private String sku;
    private Integer quantityOrdered;
    private boolean freeShipping;
    private int quantity;
    private int quantityOrderMax;
    private int quantityOrderMin = 1;
    private String status;
    private BigDecimal price;
    private BigDecimal specialPrice;
    private LocalDateTime specialStart;
    private LocalDateTime specialEnd;
    private String publisher;
    private String format;
    private String country;
    private String style;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateAvailable;
    private BigInteger categoryId;
}
