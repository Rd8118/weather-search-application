package com.rohit.weathersearch.exception;


/**
 * Custom exception thrown when a city is not found in the weather API.
 */
public class CityNotFoundException extends RuntimeException {
    
    public CityNotFoundException(String cityName) {
        super("City not found: " + cityName);
    }
    
    public CityNotFoundException(String cityName, Throwable cause) {
        super("City not found: " + cityName, cause);
    }
}
