package com.dutq.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataException extends Exception {
    public DataException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
