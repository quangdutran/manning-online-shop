package org.dutq.service.product;

import com.dutq.core.exception.DataException;
import com.dutq.core.exception.InvalidInputException;
import com.dutq.core.model.dto.CloudinaryPhotoUploadResponse;
import com.dutq.core.model.entity.Product;
import org.dutq.service.product.repository.ManufacturerRepository;
import org.dutq.service.product.repository.ProductRepository;
import org.dutq.service.product.service.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ManufacturerRepository manufacturerRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductServiceImpl underTest;

    @DisplayName("If getting the byte array from the photo not working, throw exception")
    @Test
    public void createProduct_whenFileMalfunction() throws IOException, InvalidInputException, DataException {
        MultipartFile invalidFile = mock(MultipartFile.class);
        when(invalidFile.getBytes()).thenThrow(new IOException());
        Product product = new Product();

        Exception exception = assertThrows(InvalidInputException.class, () -> {
            underTest.upsertProduct(product, invalidFile);
        });
        assertTrue(exception.getMessage().contains("Having problem getting bytes from the photo file"));
    }

    @DisplayName("If the photo could not be uploaded to Cloudinary, throw exception")
    @Test
    public void createProduct_whenCloudinaryServiceFails() throws IOException {
        MultipartFile invalidFile = mock(MultipartFile.class);
        when(invalidFile.getBytes()).thenReturn("TEST".getBytes());
        Product product = new Product();
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), any()))
            .thenThrow(RestClientException.class);

        Exception exception = assertThrows(DataException.class, () -> {
            underTest.upsertProduct(product, invalidFile);
        });
        assertTrue(exception.getMessage().contains("Fail to send photo upload request to Cloudinary"));
    }

    @DisplayName("After photo are uploaded and saving product to db fails, then remove photo in cloudinary")
    @Test
    public void createProduct_whenSavingProductFails() throws IOException {
        String mockCloudinaryPublicID = "TEST_ID";
        MultipartFile invalidFile = mock(MultipartFile.class);
        when(invalidFile.getBytes()).thenReturn("TEST".getBytes());
        Product product = new Product();
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), any()))
                .thenReturn(new ResponseEntity(new CloudinaryPhotoUploadResponse("FILE_NAME","TEST_URL", mockCloudinaryPublicID), HttpStatus.OK));

        when(productRepository.saveAndFlush(product)).thenThrow(RuntimeException.class);
        Exception exception = assertThrows(DataException.class, () -> {
            underTest.upsertProduct(product, invalidFile);
        });
        assertTrue(exception.getMessage().contains("Having problem saving product in ProductServiceImpl"));
        verify(restTemplate).delete(contains(mockCloudinaryPublicID));
    }


}

