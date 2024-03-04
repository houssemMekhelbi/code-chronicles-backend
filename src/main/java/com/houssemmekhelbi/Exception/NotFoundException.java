package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundException.class);

    public NotFoundException(String message) {
        super(message);
        logger.error(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}