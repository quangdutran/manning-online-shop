package com.dutq.core.model.converter;

import com.dutq.core.model.dto.ManufacturerDTO;
import com.dutq.core.model.dto.ProductCategoryDTO;
import com.dutq.core.model.dto.ProductDTO;
import com.dutq.core.model.entity.Manufacturer;
import com.dutq.core.model.entity.Photo;
import com.dutq.core.model.entity.Product;
import com.dutq.core.model.entity.ProductCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DTOConverter {
    public static Manufacturer dtoToEntity(ManufacturerDTO dto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(dto.getName());
        manufacturer.setDescription(dto.getDescription());
        manufacturer.setImage(dto.getImage());
        manufacturer.setUrl(dto.getUrl());
        return manufacturer;
    }

    public static ManufacturerDTO entityToDTO(Manufacturer manufacturer) {
        return Objects.isNull(manufacturer) ? null :
                ManufacturerDTO.builder()
                .description(manufacturer.getDescription())
                .id(manufacturer.getId())
                .image(manufacturer.getImage())
                .url(manufacturer.getUrl())
                .name(manufacturer.getName()).build();
    }

    public static ProductDTO entityToDTO(Product product) {
        List<Photo> photos = product.getPhotos();
        String mainPhotoLink = Objects.nonNull(photos) ?
                photos.stream().filter(Photo::isDefaultPhoto).map(Photo::getUrl).collect(Collectors.joining())
                : StringUtils.EMPTY;
        List<String> photoLinks = Objects.nonNull(photos) ?
                photos.stream().map(Photo::getUrl).collect(Collectors.toList())
                : new ArrayList<>();
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getProductDescription().getDescription())
                .keywords(product.getProductDescription().getKeywords())
                .link(product.getProductDescription().getLink())
                .metaDesc(product.getProductDescription().getMetaDesc())
                .name(product.getProductDescription().getName())
                .preOrder(product.isPreOrder())
                .manufacturer(entityToDTO(product.getManufacturer()))
                .dateAvailable(product.getDateAvailable())
                .freeShipping(product.getProductDescription().isFreeShipping())
                .quantity(product.getProductDescription().getQuantity())
                .quantityOrderMax(product.getProductDescription().getQuantityOrderMax())
                .quantityOrderMin(product.getProductDescription().getQuantityOrderMin())
                .price(product.getProductDescription().getPrice())
                .publisher(product.getProductDescription().getPublisher())
                .country(product.getProductDescription().getCountry())
                .formatType(product.getProductDescription().getFormatType())
                .style(product.getProductDescription().getStyle())
                .mainPhotoLink(mainPhotoLink)
                .photoLinks(photoLinks).build();
    }

    public static ProductCategoryDTO entityToDTO(ProductCategory category) {
        List<ProductCategoryDTO> children = CollectionUtils.isEmpty(category.getChildCategories()) ? null :
                category.getChildCategories().stream().map(DTOConverter::entityToDTO).collect(Collectors.toList());
        return ProductCategoryDTO.builder().id(category.getId())
                .imageUrl(category.getImageUrl())
                .parentId(Objects.nonNull(category.getParent()) ? category.getParent().getId() : null)
                .children(children)
                .name(category.getName())
                .build();
    }
}
