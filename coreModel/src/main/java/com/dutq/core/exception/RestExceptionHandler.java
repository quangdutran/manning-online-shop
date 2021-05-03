package com.dutq.core.exception;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@CommonsLog
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
//        log.error(ex);
//        List<String> details = new ArrayList<>();
//        details.add(ex.getLocalizedMessage());
//        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
//        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<Object> handleDataExceptions(DataException ex) {
        log.error(ex.getMessage(), ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity(error, error.getStatus());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputExceptions(InvalidInputException ex) {
        log.error(ex.getMessage(), ex);
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity(error, error.getStatus());
    }
}
