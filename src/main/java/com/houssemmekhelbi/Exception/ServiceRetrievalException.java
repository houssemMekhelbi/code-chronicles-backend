package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceRetrievalException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRetrievalException.class);

    public ServiceRetrievalException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
    public ServiceRetrievalException(String message) {
        super(message);
        logger.error(message);
    }
}