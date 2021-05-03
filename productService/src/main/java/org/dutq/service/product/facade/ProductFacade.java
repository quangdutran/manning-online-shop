package org.dutq.service.product.facade;

import com.dutq.core.exception.DataException;
import com.dutq.core.exception.InvalidInputException;
import com.dutq.core.model.dto.FilesUploadResponse;
import com.dutq.core.model.dto.ProductCategoryDTO;
import com.dutq.core.model.dto.ProductDTO;
import com.dutq.core.model.entity.ProductCategory;
import com.dutq.core.model.request.CreateProductCategoryRequest;
import com.dutq.core.model.request.UpsertProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;


public interface ProductFacade {
    ProductDTO createProduct(UpsertProductRequest request) throws DataException, InvalidInputException;
    ProductDTO updateProduct(UpsertProductRequest request, String productId) throws DataException, InvalidInputException;
    ProductDTO createProduct(UpsertProductRequest request, List<MultipartFile> photos) throws InvalidInputException, DataException;
    ProductDTO getProductById(String productId) throws InvalidInputException;
    FilesUploadResponse uploadProductPhotos(String productId, List<MultipartFile> photos) throws InvalidInputException, InterruptedException;
    void deleteProduct(String productId) throws InvalidInputException;
    List<ProductDTO> getProducts();
    ProductCategoryDTO createProductCategory(CreateProductCategoryRequest request) throws InvalidInputException;
    List<ProductCategoryDTO> createProductCategories(List<CreateProductCategoryRequest> request) throws InvalidInputException;
    ProductCategoryDTO getProductCategoryById(BigInteger id) throws InvalidInputException;
    List<ProductCategoryDTO> getProductCategories();
}
