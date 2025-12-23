package com.rohit.weathersearch.exception;


/**
 * Custom exception thrown when external API call fails.
 */
public class ExternalApiException extends RuntimeException {
    
    public ExternalApiException(String message) {
        super(message);
    }
    
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}