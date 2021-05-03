package com.dutq.core.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_description")
public class ProductDescription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String description;
    private String name;
    private String metaDesc;
    private String keywords;
    private String link;
    private boolean freeShipping;
    private Integer quantity;
    private Integer quantityOrderMax;
    private int quantityOrderMin = 1;
    private String status;
    private BigDecimal price;
    private BigDecimal specialPrice;
    private LocalDateTime specialStart;
    private LocalDateTime specialEnd;

    //For CD
    private String publisher;
    private String formatType;
    private String country;
    private String style;

    public ProductDescription() {
    }

    public ProductDescription(String description, String name, String metaDesc, String keywords, String link) {
        this.description = description;
        this.name = name;
        this.metaDesc = metaDesc;
        this.keywords = keywords;
        this.link = link;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityOrderMax() {
        return quantityOrderMax;
    }

    public void setQuantityOrderMax(Integer quantityOrderMax) {
        this.quantityOrderMax = quantityOrderMax;
    }

    public int getQuantityOrderMin() {
        return quantityOrderMin;
    }

    public void setQuantityOrderMin(int quantityOrderMin) {
        this.quantityOrderMin = quantityOrderMin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(BigDecimal specialPrice) {
        this.specialPrice = specialPrice;
    }

    public LocalDateTime getSpecialStart() {
        return specialStart;
    }

    public void setSpecialStart(LocalDateTime specialStart) {
        this.specialStart = specialStart;
    }

    public LocalDateTime getSpecialEnd() {
        return specialEnd;
    }

    public void setSpecialEnd(LocalDateTime specialEnd) {
        this.specialEnd = specialEnd;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(String metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
