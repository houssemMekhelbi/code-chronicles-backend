package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServiceNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ServiceNotFoundException.class);

    public ServiceNotFoundException(String message) {
        super(message);
        logger.error(message);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}