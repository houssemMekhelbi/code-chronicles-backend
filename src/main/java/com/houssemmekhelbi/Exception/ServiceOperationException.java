package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceOperationException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ServiceOperationException.class);

    public ServiceOperationException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public ServiceOperationException(String message) {
        super(message);
        logger.error(message);
    }
}
