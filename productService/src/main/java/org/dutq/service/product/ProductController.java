package org.dutq.service.product;

import com.dutq.core.exception.DataException;
import com.dutq.core.exception.InvalidInputException;
import com.dutq.core.model.converter.DTOConverter;
import com.dutq.core.model.dto.FilesUploadResponse;
import com.dutq.core.model.dto.ProductCategoryDTO;
import com.dutq.core.model.dto.ProductDTO;
import com.dutq.core.model.entity.ProductCategory;
import com.dutq.core.model.request.CreateProductCategoryRequest;
import com.dutq.core.model.request.UpsertProductRequest;
import com.dutq.core.utils.Utils;
import org.dutq.service.product.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductFacade productFacade;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody UpsertProductRequest request) throws DataException, InvalidInputException {
        return new ResponseEntity(productFacade.createProduct(request), HttpStatus.OK);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody UpsertProductRequest request,
                                                    @PathVariable("productId") String productId) throws InvalidInputException, DataException {
        return new ResponseEntity(productFacade.updateProduct(request, productId), HttpStatus.OK);
    }

    @PostMapping(value = "/{productId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FilesUploadResponse> uploadProductPhoto(@PathVariable("productId") String productId,
                                                                  @RequestParam("files") MultipartFile[] photos) throws InvalidInputException, InterruptedException {
        FilesUploadResponse response = productFacade.uploadProductPhotos(productId, Arrays.asList(photos));
        return new ResponseEntity<>(response, response.getResult());
    }

    @PostMapping(value = "/pic" ,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ProductDTO> createProductWithPhotos(@RequestPart("product") String productJson,
                                                           @RequestPart("photos") List<MultipartFile> files)
            throws InvalidInputException, DataException {
            UpsertProductRequest request = Utils.mapToObject(productJson, UpsertProductRequest.class);
            return new ResponseEntity(productFacade.createProduct(request, files), HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") String productId) throws InvalidInputException {
        return new ResponseEntity<>(productFacade.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getProducts() throws InvalidInputException {
        return new ResponseEntity<>(productFacade.getProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId) throws InvalidInputException {
        productFacade.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody CreateProductCategoryRequest request) throws InvalidInputException {
        return new ResponseEntity(productFacade.createProductCategory(request), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<List<ProductCategoryDTO>> createProductCategories(@RequestBody List<CreateProductCategoryRequest> request) throws InvalidInputException {
        return new ResponseEntity(productFacade.createProductCategories(request), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryDTO>> getProductCategories() {
        return new ResponseEntity(productFacade.getProductCategories(), HttpStatus.OK);
    }

}
