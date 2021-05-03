package org.dutq.service.product.service;

import com.dutq.core.exception.InvalidInputException;

public abstract class AbstractService {
    private void handleException(Exception ex) throws Exception {
        if (ex instanceof InvalidInputException) {
            throw ex;
        }
    }
}
