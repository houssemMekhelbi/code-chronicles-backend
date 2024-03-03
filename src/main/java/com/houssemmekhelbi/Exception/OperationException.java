package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(OperationException.class);

    public OperationException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public OperationException(String message) {
        super(message);
        logger.error(message);
    }
}
