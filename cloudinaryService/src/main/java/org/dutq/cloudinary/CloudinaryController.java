package org.dutq.cloudinary;

import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.dutq.core.model.dto.CloudinaryPhotoUploadResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/cloudinary")
@CommonsLog
public class CloudinaryController {
    @Autowired
    private CloudinaryConfiguration configuration;

    @PostMapping(value = "/photo/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CloudinaryPhotoUploadResponse> uploadPhoto(@RequestParam("file") MultipartFile photo) throws IOException {
        return new ResponseEntity(sendPhoto(photo), HttpStatus.OK);
    }

    private CloudinaryPhotoUploadResponse sendPhoto(MultipartFile photo) throws IOException {
        Map uploadResult = configuration.getCloudinaryConfig().uploader().upload(photo.getBytes(), ObjectUtils.emptyMap());
        String url = Objects.isNull(uploadResult.get("url")) ? StringUtils.EMPTY : (String) uploadResult.get("url");
        String publicId = Objects.isNull(uploadResult.get("public_id")) ? StringUtils.EMPTY : (String) uploadResult.get("public_id");
        return new CloudinaryPhotoUploadResponse(photo.getOriginalFilename(), url, publicId);
    }

    @PostMapping(value = "/photos/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<CloudinaryPhotoUploadResponse>> uploadPhotos(@RequestParam("files") MultipartFile[] photos) throws IOException {
        List<MultipartFile> photoList = Arrays.asList(photos);
        List<CloudinaryPhotoUploadResponse> result = new ArrayList<>();
            for (MultipartFile photo : photoList) {
                try {
                    result.add(sendPhoto(photo));
                } catch (Exception e) {
                    log.error("Fail to upload file to Cloudinary", e);
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/photo/{public_id}")
    public ResponseEntity<String> deletePhoto(@PathVariable("public_id") String publicId) throws IOException {
        Map result = configuration.getCloudinaryConfig().uploader().destroy(publicId, ObjectUtils.emptyMap());
        return new ResponseEntity<>((String) result.get("result"), HttpStatus.OK);
    }
}
