package org.dutq.service.product.facade;

import com.dutq.core.exception.DataException;
import com.dutq.core.exception.InvalidInputException;
import com.dutq.core.model.converter.DTOConverter;
import com.dutq.core.model.dto.FilesUploadResponse;
import com.dutq.core.model.dto.FileUploadResponse;
import com.dutq.core.model.dto.ProductCategoryDTO;
import com.dutq.core.model.dto.ProductDTO;
import com.dutq.core.model.entity.Manufacturer;
import com.dutq.core.model.entity.Product;
import com.dutq.core.model.entity.ProductCategory;
import com.dutq.core.model.entity.ProductDescription;
import com.dutq.core.model.request.CreateProductCategoryRequest;
import com.dutq.core.model.request.UpsertProductRequest;
import com.dutq.core.utils.Utils;
import org.dutq.service.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductFacadeImpl implements ProductFacade {
    @Autowired
    private ProductService productService;

    @Override
    public ProductDTO createProduct(UpsertProductRequest request) throws DataException, InvalidInputException {
        Product product = new Product();
        buildProductFromRequest(request, product);
        productService.upsertProduct(product);
        return DTOConverter.entityToDTO(product);
    }

    @Override
    public ProductDTO updateProduct(UpsertProductRequest request, String productId) throws DataException, InvalidInputException {
        Product product = productService.getProductById(productId);
        buildProductFromRequest(request, product);
        productService.upsertProduct(product);
        return DTOConverter.entityToDTO(product);
    }

    @Override
    public ProductDTO createProduct(UpsertProductRequest request, List<MultipartFile> photos) throws InvalidInputException, DataException {
        Product product = new Product();
        buildProductFromRequest(request, product);
        productService.upsertProduct(product, photos.get(0));
        return DTOConverter.entityToDTO(product);
    }

    @Override
    public ProductDTO getProductById(String productId) throws InvalidInputException {
        return DTOConverter.entityToDTO(productService.getProductById(productId));
    }

    private void buildProductFromRequest(UpsertProductRequest request, Product product) throws InvalidInputException {
        product.setSKU(request.getSku());
        product.setDateAvailable(request.getDateAvailable());
        product.setQuantityOrdered(request.getQuantityOrdered());

        //Build product description
        ProductDescription productDescription = new ProductDescription();
        productDescription.setDescription(request.getDescription());
        productDescription.setKeywords(request.getKeywords());
        productDescription.setLink(request.getLink());
        productDescription.setMetaDesc(request.getMetaDesc());
        productDescription.setName(request.getName());
        productDescription.setFreeShipping(request.isFreeShipping());
        productDescription.setQuantity(request.getQuantity());
        productDescription.setQuantityOrderMax(request.getQuantityOrderMax());
        productDescription.setQuantityOrderMin(request.getQuantityOrderMin());
        productDescription.setStatus(request.getStatus());
        productDescription.setPrice(request.getPrice());
        productDescription.setSpecialPrice(request.getSpecialPrice());
        productDescription.setSpecialStart(request.getSpecialStart());
        productDescription.setSpecialEnd(request.getSpecialEnd());
        productDescription.setPublisher(request.getPublisher());
        productDescription.setFormatType(request.getFormat());
        productDescription.setCountry(request.getCountry());
        productDescription.setStyle(request.getStyle());


        product.setProductDescription(productDescription);

        //Check manufacturer
        if (Objects.nonNull(request.getManufacturer())) {
            Manufacturer manufacturer;
            if (Objects.nonNull(request.getManufacturer().getId())) {
                manufacturer = productService.getManufacturerById(request.getManufacturer().getId());
            } else {
                manufacturer = DTOConverter.dtoToEntity(request.getManufacturer());
            }
            product.setManufacturer(manufacturer);
        }

        //Check category
        if (Objects.nonNull(request.getCategoryId())) {
            ProductCategory category = productService.getProductCategoryById(request.getCategoryId());
            product.setCategory(category);
        }
    }

    @Override
    public FilesUploadResponse uploadProductPhotos(String productId, List<MultipartFile> photos) throws InvalidInputException, InterruptedException {
        List<FileUploadResponse> validationResponses = validatePhotos(photos);
        if (!validationResponses.isEmpty()) return FilesUploadResponse.builder()
                .result(HttpStatus.BAD_REQUEST).details(validationResponses).build();
        Set<String> uploadResult = productService.uploadProductPhotos(productId, photos);
        return FilesUploadResponse.builder()
                .result(uploadResult.isEmpty() ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK)
                .details(uploadResult.stream().map(p -> FileUploadResponse.builder().result(p).build())
                    .collect(Collectors.toList())).build();
    }

    private List<FileUploadResponse> validatePhotos(List<MultipartFile> photos) throws InvalidInputException {
        if (photos.size() > 5) throw new InvalidInputException("5 files upload maximum");
        List<FileUploadResponse> validation = new ArrayList<>();
        photos.forEach(photo -> {
            if (Utils.validateImageFileExtension(photo)) {
                validation.add(FileUploadResponse.builder().fileName(photo.getOriginalFilename())
                        .result("Invalid file extension").build());
            } else if (photo.getSize() > 2000000) { // 2MB
                validation.add(FileUploadResponse.builder().fileName(photo.getOriginalFilename())
                        .result("File too large").build());
            }
        });
        return validation;
    }

    @Override
    public void deleteProduct(String productId) throws InvalidInputException {
        productService.deleteProduct(productId);
    }

    @Override
    public List<ProductDTO> getProducts() {
        return productService.getProducts()
                .stream().map(DTOConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO createProductCategory(CreateProductCategoryRequest request) throws InvalidInputException {
        return DTOConverter.entityToDTO(productService.upsertProductCategory(buildProductCategoryFromRequest(request)));
    }

    @Override
    public ProductCategoryDTO getProductCategoryById(BigInteger id) throws InvalidInputException {
        return DTOConverter.entityToDTO(productService.getProductCategoryById(id));
    }

    @Override
    public List<ProductCategoryDTO> createProductCategories(List<CreateProductCategoryRequest> request) throws InvalidInputException {
        List<ProductCategory> categories = buildProductCategoriesFromRequest(request);
        return productService.upsertProductCategories(categories).stream()
                .map(DTOConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    private ProductCategory buildProductCategoryFromRequest(CreateProductCategoryRequest request) throws InvalidInputException {
        ProductCategory category = new ProductCategory();
        category.setImageUrl(request.getImageUrl());
        category.setName(request.getName());
        category.setCategoryCode(request.getCategoryCode());
        if (Objects.nonNull(request.getParentId())) {
            category.setParent(productService.getProductCategoryById(request.getParentId()));
        }
        return category;
    }

    private ProductCategory buildProductCategory(CreateProductCategoryRequest request, int depth) {
        ProductCategory category = new ProductCategory();
        category.setImageUrl(request.getImageUrl());
        category.setName(request.getName());
        category.setCategoryCode(request.getCategoryCode());
        category.setChildCategories(new HashSet<>());
        //Stop at 4 level of category hierarchy
        if (!CollectionUtils.isEmpty(request.getChildCategories()) && depth < 4) {
            request.getChildCategories().forEach(c -> {
                        ProductCategory child = buildProductCategory(c, depth + 1);
                        child.setParent(category);
                        category.getChildCategories().add(child);
                    });
        }
        return category;
    }

    private List<ProductCategory> buildProductCategoriesFromRequest(List<CreateProductCategoryRequest> requests) {
        return requests.stream().map(r -> buildProductCategory(r, 0)).collect(Collectors.toList());
    }

    @Override
    public List<ProductCategoryDTO> getProductCategories() {
        return productService.getProductCategories().stream()
                .map(DTOConverter::entityToDTO).collect(Collectors.toList());
    }
}
