package org.dutq.service.product.service;

import com.dutq.core.exception.DataException;
import com.dutq.core.exception.InvalidInputException;
import com.dutq.core.model.dto.CloudinaryPhotoUploadResponse;
import com.dutq.core.model.entity.Manufacturer;
import com.dutq.core.model.entity.Photo;
import com.dutq.core.model.entity.Product;
import com.dutq.core.model.entity.ProductCategory;
import lombok.extern.apachecommons.CommonsLog;
import org.dutq.service.product.repository.ProductRepository;
import org.dutq.service.product.repository.ManufacturerRepository;
import org.dutq.service.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public Product upsertProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product upsertProduct(Product product, MultipartFile photo) throws InvalidInputException, DataException {
        Photo productPhoto = new Photo();
        CloudinaryPhotoUploadResponse photoUploadResponse = sendFileToCloudinary(photo);
        productPhoto.setUrl(photoUploadResponse.getUrl());
        productPhoto.setCloudinaryPublicId(photoUploadResponse.getPublicId());
        product.setPhotos(Arrays.asList(productPhoto));
        try {
            return productRepository.saveAndFlush(product);
        } catch (Exception ex) {
            log.error("Having problem saving product " + product.getSKU() + " in ProductServiceImpl", ex);
            sendPhotosRemoveRequestAsync(Arrays.asList(productPhoto.getCloudinaryPublicId()));
            throw new DataException("Having problem saving product in ProductServiceImpl", ex);
        }
    }

    private void sendPhotosRemoveRequestAsync(List<String> publicIds) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List removeTasks = publicIds.stream()
                .map(id -> ((Runnable) () -> restTemplate.delete("http://localhost:8083/cloudinary/photo/" + id)))
                .map(Executors::callable)
                .collect(Collectors.toList());
        try {
            executor.invokeAll(removeTasks);
        } catch (InterruptedException e) {
            log.error("Fail to send remove photos to Cloudinary", e);
        }
        executor.shutdownNow();
    }

    private void sendPhotoRemoveRequestAsync(String publicId) {
        try {
            Thread newThread = new Thread(() -> {
                restTemplate.delete("http://localhost:8083/cloudinary/photo/" + publicId);
            });
            newThread.start();
        } catch (Exception ex) {
            log.error("Failed to send removing photo to Cloudinary with id " + publicId);
        }
    }

    private CloudinaryPhotoUploadResponse sendFileToCloudinary(MultipartFile photo) throws InvalidInputException, DataException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(photo.getBytes()) {
                @Override
                public String getFilename() {
                    return photo.getOriginalFilename();
                }
            });
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            String serverUrl = "http://localhost:8083/cloudinary/photo/upload";
            ResponseEntity<CloudinaryPhotoUploadResponse> response = restTemplate
                    .postForEntity(serverUrl, requestEntity, CloudinaryPhotoUploadResponse.class);
            return response.getBody();
        } catch (Exception ex) {
            handleSendingFilesToCloudinaryException(ex);
            return null;
        }
    }

    private void handleSendingFilesToCloudinaryException(Exception ex) throws InvalidInputException, DataException {
        if (ex instanceof IOException) {
            log.error("Having problem getting bytes from the photo file", ex);
            throw new InvalidInputException("Having problem getting bytes from the photo file", ex);
        } else {
            log.error("Fail to send photo upload request to Cloudinary", ex);
            throw new DataException("Fail to send photo upload request to Cloudinary", ex);
        }
    }


    private List<CloudinaryPhotoUploadResponse> sendFilesToCloudinary(List<MultipartFile> photos) throws InterruptedException {
        List<CloudinaryPhotoUploadResponse> result = new ArrayList<>();
        if (photos.size() > 2) {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
                Callable<CloudinaryPhotoUploadResponse> sendfile = () -> sendFileToCloudinary(photos.get(0));

            List sendFileTasks =
                    photos.stream().map(p -> ((Callable) () -> sendFileToCloudinary(p)))
                            .collect(Collectors.toList());
            List<Future<CloudinaryPhotoUploadResponse>> futures = executorService.invokeAll(sendFileTasks);
            for (Future<CloudinaryPhotoUploadResponse> f : futures) {
                try {
                    result.add(f.get());
                } catch (Exception ignored) {
                    log.error("Failed to send file to Cloudinary", ignored);
                }
            }
            executorService.shutdownNow();
        } else {
            for (MultipartFile file : photos) {
                try {
                    result.add(sendFileToCloudinary(file));
                } catch (Exception ignored) {
                    log.error("Failed to send file to Cloudinary", ignored);
                }
            }
        }
        return result;
    }

    @Override
    public Manufacturer getManufacturerById(BigInteger id) {
        return manufacturerRepository.getOne(id);
    }

    @Override
    public Product getProductById(String id) throws InvalidInputException {
        Optional<Product> product = productRepository.getProductById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            log.error("Could not find product with id " + id);
            throw new InvalidInputException("Could not find product with id " + id);
        }
    }

    @Override
    public Set<String> uploadProductPhotos(String id, List<MultipartFile> photos) throws InvalidInputException, InterruptedException {
        Product product = getProductById(id);
        List<CloudinaryPhotoUploadResponse> responses = sendFilesToCloudinary(photos);
        product.setPhotos(responses.stream().map(r -> new Photo(r.getUrl(), r.getPublicId())).collect(Collectors.toList()));
        productRepository.save(product);
        return responses.stream().map(CloudinaryPhotoUploadResponse::getUrl)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteProduct(String productId) throws InvalidInputException {
        Product product = getProductById(productId);
        List<String> productPhotosPublicIds = product.getPhotos().stream()
                .map(Photo::getCloudinaryPublicId).collect(Collectors.toList());
        sendPhotosRemoveRequestAsync(productPhotosPublicIds);
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findTop10OrOrderByCreatedDate();
    }

    @Override
    public ProductCategory getProductCategoryById(BigInteger id) throws InvalidInputException {
        Optional<ProductCategory> category = productCategoryRepository.getProductCategoryById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            log.error("Could not find category with id " + id);
            throw new InvalidInputException("Could not find category with id " + id);
        }
    }

    @Override
    public ProductCategory upsertProductCategory(ProductCategory category) throws InvalidInputException {
        return productCategoryRepository.saveAndFlush(category);
    }

    @Override
    public List<ProductCategory> upsertProductCategories(List<ProductCategory> categories) {
        return productCategoryRepository.saveAll(categories);
    }

    @Override
    public List<ProductCategory> getProductCategories() {
        return productCategoryRepository.getProductCategories();
    }
}
