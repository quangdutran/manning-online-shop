package com.dutq.core.utils;

import com.dutq.core.exception.InvalidInputException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.regex.Pattern;


@CommonsLog
public class Utils {
    public static <T> T mapToObject(String input, Class<T> classOb) throws InvalidInputException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return objectMapper.readValue(input, classOb);
        } catch (JsonProcessingException e) {
            log.error("Could not convert String to " + classOb.getSimpleName(), e);
            throw new InvalidInputException("Could not convert String to " + classOb.getSimpleName(), e);
        }
    }

    public static boolean validateImageFileExtension(MultipartFile file) {
        Pattern pattern = Pattern.compile("([^\\\\s]+(\\\\.(?i)(jpg|png|gif|bmp|jpeg))$)");
        return Objects.nonNull(file.getOriginalFilename()) &&
                pattern.matcher(file.getOriginalFilename()).matches();
    }
}
