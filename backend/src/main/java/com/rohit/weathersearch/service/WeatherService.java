package com.rohit.weathersearch.service;


import com.rohit.weathersearch.model.dto.CacheStatsResponse;
import com.rohit.weathersearch.model.dto.WeatherResponse;

/**
 * Service interface for weather operations.
 * Defines contract for weather data retrieval and cache management.
 */
public interface WeatherService {
    
    /**
     * Retrieves current weather for a given city.
     * Results are cached to improve performance.
     * 
     * @param cityName Name of the city
     * @return WeatherResponse containing weather information
     */
    WeatherResponse getCurrentWeather(String cityName);
    
    /**
     * Retrieves cache statistics for monitoring.
     * 
     * @return CacheStatsResponse containing cache performance metrics
     */
    CacheStatsResponse getCacheStatistics();
}