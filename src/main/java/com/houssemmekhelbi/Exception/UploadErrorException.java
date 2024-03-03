package com.houssemmekhelbi.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UploadErrorException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(UploadErrorException.class);

    public UploadErrorException(String message) {
        super(message);
        logger.error(message);
    }

    public UploadErrorException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}