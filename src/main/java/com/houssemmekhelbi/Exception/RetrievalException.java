package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetrievalException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RetrievalException.class);

    public RetrievalException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
    public RetrievalException(String message) {
        super(message);
        logger.error(message);
    }
}