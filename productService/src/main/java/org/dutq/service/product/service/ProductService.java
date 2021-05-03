package org.dutq.service.product.service;

import com.dutq.core.exception.DataException;
import com.dutq.core.exception.InvalidInputException;
import com.dutq.core.model.entity.Manufacturer;
import com.dutq.core.model.entity.Product;
import com.dutq.core.model.entity.ProductCategory;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Product upsertProduct(Product product) throws DataException;
    Product upsertProduct(Product product, MultipartFile photo) throws InvalidInputException, DataException;
    Manufacturer getManufacturerById(BigInteger id);
    Product getProductById(String id) throws InvalidInputException;
    Set<String> uploadProductPhotos(String id, List<MultipartFile> photos) throws InvalidInputException, InterruptedException;
    void deleteProduct(String productId) throws InvalidInputException;
    List<Product> getProducts();
    ProductCategory getProductCategoryById(BigInteger id) throws InvalidInputException;
    ProductCategory upsertProductCategory(ProductCategory category) throws InvalidInputException;
    List<ProductCategory> upsertProductCategories(List<ProductCategory> categories);
    List<ProductCategory> getProductCategories();
}
