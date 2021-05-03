package com.dutq.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudinaryPhotoUploadResponse {
    private String fileName;
    private String url;
    private String publicId;
}
