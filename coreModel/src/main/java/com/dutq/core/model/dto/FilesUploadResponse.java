package com.dutq.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class FilesUploadResponse {
    private HttpStatus result;
    private List<FileUploadResponse> details;
}
