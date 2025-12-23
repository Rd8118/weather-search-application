package com.rohit.weathersearch.controller;

import com.rohit.weathersearch.model.dto.CacheStatsResponse;
import com.rohit.weathersearch.model.dto.WeatherResponse;
import com.rohit.weathersearch.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for weather-related endpoints.
 * Provides APIs for fetching weather data and cache statistics.
 */
@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "${cors.allowed-origins}")
@Validated
public class WeatherController {
    
    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
    
    private final WeatherService weatherService;
    
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    /**
     * GET /api/weather?city={cityName}
     * 
     * Retrieves current weather information for a given city.
     * 
     * @param city Name of the city (required, cannot be blank)
     * @return ResponseEntity containing WeatherResponse
     * 
     * Example: GET /api/weather?city=London
     * Response: 200 OK with weather data
     */
    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(
            @RequestParam @NotBlank(message = "City name is required") String city) {
        
        log.info("Received request for weather data: city={}", city);
        
        WeatherResponse response = weatherService.getCurrentWeather(city);
        
        log.info("Successfully retrieved weather for: {}", city);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/weather/cache/stats
     * 
     * Retrieves cache performance statistics.
     * Useful for monitoring and debugging cache behavior.
     * 
     * @return ResponseEntity containing CacheStatsResponse
     * 
     * Example: GET /api/weather/cache/stats
     * Response: 200 OK with cache statistics
     */
    @GetMapping("/cache/stats")
    public ResponseEntity<CacheStatsResponse> getCacheStats() {
        
        log.debug("Received request for cache statistics");
        
        CacheStatsResponse stats = weatherService.getCacheStatistics();
        
        return ResponseEntity.ok(stats);
    }
    
    /**
     * GET /api/weather/health
     * 
     * Simple health check endpoint.
     * 
     * @return ResponseEntity with health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Weather Service is running!");
    }
}