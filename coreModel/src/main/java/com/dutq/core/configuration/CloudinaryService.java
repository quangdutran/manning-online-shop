package com.dutq.core.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    @Value("cloudinary.name")
    private String name;

    @Value("cloudinary.api.key")
    private String apiKey;

    @Value("cloudinary.api.secret")
    private String apiSecret;

    public Cloudinary getCloudinaryConfig() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", name,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public String uploadPhoto(MultipartFile photo) throws IOException {
        Map uploadResult = getCloudinaryConfig().uploader().upload(photo, ObjectUtils.emptyMap());
        return Objects.isNull(uploadResult.get("url")) ? StringUtils.EMPTY : (String) uploadResult.get("url");
    }
}
