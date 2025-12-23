package com.rohit.weathersearch.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.rohit.weathersearch.config.CacheConfig;
import com.rohit.weathersearch.exception.CityNotFoundException;
import com.rohit.weathersearch.exception.ExternalApiException;
import com.rohit.weathersearch.model.dto.CacheStatsResponse;
import com.rohit.weathersearch.model.dto.WeatherResponse;
import com.rohit.weathersearch.model.external.OpenWeatherMapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of WeatherService.
 * Handles weather data retrieval from OpenWeatherMap API with caching.
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    
    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);
    
    private final WebClient webClient;
    private final CacheManager cacheManager;
    
    @Value("${openweather.api.key}")
    private String apiKey;
    
    public WeatherServiceImpl(WebClient webClient, CacheManager cacheManager) {
        this.webClient = webClient;
        this.cacheManager = cacheManager;
    }
    
    /**
     * Retrieves current weather with caching.
     * Cache key is the lowercase city name for case-insensitive caching.
     */
    @Override
    @Cacheable(
    	    value = CacheConfig.WEATHER_CACHE,
    	    key = "#cityName.toLowerCase().trim()",
    	    unless = "#result == null"
    	)
    public WeatherResponse getCurrentWeather(String cityName) {
        log.info("Fetching weather data for city: {}", cityName);
        
        // Validate input
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be empty");
        }
        
        // Normalize city name
        String normalizedCityName = cityName.trim();
        
        try {
            // Call OpenWeatherMap API
            OpenWeatherMapResponse apiResponse = webClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("q", normalizedCityName)
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric") // Use metric units (Celsius)
                            .build())
                    .retrieve()
                    .onStatus(
                        status -> status.is4xxClientError(),
                        response -> Mono.error(new CityNotFoundException(normalizedCityName))
                    )
                    .onStatus(
                        status -> status.is5xxServerError(),
                        response -> Mono.error(new ExternalApiException("Weather API server error"))
                    )
                    .bodyToMono(OpenWeatherMapResponse.class)
                    .block(); // Block for synchronous operation
            
            if (apiResponse == null) {
                throw new ExternalApiException("Received null response from weather API");
            }
            
            log.info("Successfully fetched weather data for: {}", apiResponse.getName());
            
            // Convert to DTO
            return convertToWeatherResponse(apiResponse, false);
            
        } catch (WebClientResponseException.NotFound e) {
            log.error("City not found: {}", normalizedCityName);
            throw new CityNotFoundException(normalizedCityName);
            
        }
//        catch (WebClientResponseException e) {
//            log.error("API error: Status {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
//            throw new ExternalApiException("Weather API returned error: " + e.getMessage());
//            
//        } 
        catch (CityNotFoundException e) {
            throw e; // let GlobalExceptionHandler handle 404
        }
        catch (ExternalApiException e) {
            throw e; // keep 502 for real API failures
        }
        catch (Exception e) {
            log.error("Unexpected error fetching weather data", e);
            throw new ExternalApiException("Unexpected error while fetching weather data", e);
        }

    }
    
    /**
     * Retrieves cache statistics from Caffeine cache.
     */
    @Override
    public CacheStatsResponse getCacheStatistics() {
        log.debug("Fetching cache statistics");
        
        try {
            org.springframework.cache.Cache springCache = cacheManager.getCache(CacheConfig.WEATHER_CACHE);
            
            if (springCache == null) {
                log.warn("Cache not found: {}", CacheConfig.WEATHER_CACHE);
                return createEmptyStats();
            }
            
            // Get native Caffeine cache
            CaffeineCache caffeineCache = (CaffeineCache) springCache;
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
            
            // Get cache statistics
            CacheStats stats = nativeCache.stats();
            
            // Build response
            CacheStatsResponse response = new CacheStatsResponse();
            response.setHitCount(stats.hitCount());
            response.setMissCount(stats.missCount());
            response.setLoadSuccessCount(stats.loadSuccessCount());
            response.setLoadFailureCount(stats.loadFailureCount());
            response.setTotalLoadTime(stats.totalLoadTime());
            response.setEvictionCount(stats.evictionCount());
            response.setHitRate(stats.hitRate());
            response.setMissRate(stats.missRate());
            response.setEstimatedSize(nativeCache.estimatedSize());
            
            log.debug("Cache stats - Hits: {}, Misses: {}, Hit Rate: {}%", 
                    stats.hitCount(), stats.missCount(), stats.hitRate() * 100);
            
            return response;
            
        } catch (Exception e) {
            log.error("Error fetching cache statistics", e);
            return createEmptyStats();
        }
    }
    
    /**
     * Converts OpenWeatherMap API response to our DTO.
     */
    private WeatherResponse convertToWeatherResponse(OpenWeatherMapResponse apiResponse, boolean fromCache) {
        WeatherResponse response = new WeatherResponse();
        
        // Basic information
        response.setCityName(apiResponse.getName());
        response.setCountry(apiResponse.getSys() != null ? apiResponse.getSys().getCountry() : null);
        
        // Temperature data
        if (apiResponse.getMain() != null) {
            response.setTemperature(apiResponse.getMain().getTemp());
            response.setFeelsLike(apiResponse.getMain().getFeelsLike());
            response.setTempMin(apiResponse.getMain().getTempMin());
            response.setTempMax(apiResponse.getMain().getTempMax());
            response.setHumidity(apiResponse.getMain().getHumidity());
            response.setPressure(apiResponse.getMain().getPressure());
        }
        
        // Weather description
        if (apiResponse.getWeather() != null && !apiResponse.getWeather().isEmpty()) {
            response.setWeatherMain(apiResponse.getWeather().get(0).getMain());
            response.setWeatherDescription(apiResponse.getWeather().get(0).getDescription());
            response.setWeatherIcon(apiResponse.getWeather().get(0).getIcon());
        }
        
        // Wind data
        if (apiResponse.getWind() != null) {
            response.setWindSpeed(apiResponse.getWind().getSpeed());
            response.setWindDegree(apiResponse.getWind().getDeg());
        }
        
        // Other data
        if (apiResponse.getClouds() != null) {
            response.setCloudiness(apiResponse.getClouds().getAll());
        }
        response.setVisibility(apiResponse.getVisibility());
        
        // Sunrise/Sunset
        if (apiResponse.getSys() != null) {
            response.setSunrise(apiResponse.getSys().getSunrise());
            response.setSunset(apiResponse.getSys().getSunset());
        }
        
        // Coordinates
        if (apiResponse.getCoord() != null) {
            response.setLatitude(apiResponse.getCoord().getLat());
            response.setLongitude(apiResponse.getCoord().getLon());
        }
        
        // Metadata
        response.setTimestamp(LocalDateTime.now());
        response.setFromCache(fromCache);
        
        return response;
    }
    
    /**
     * Creates empty cache statistics response.
     */
    private CacheStatsResponse createEmptyStats() {
        CacheStatsResponse response = new CacheStatsResponse();
        response.setHitCount(0L);
        response.setMissCount(0L);
        response.setLoadSuccessCount(0L);
        response.setLoadFailureCount(0L);
        response.setTotalLoadTime(0L);
        response.setEvictionCount(0L);
        response.setHitRate(0.0);
        response.setMissRate(0.0);
        response.setEstimatedSize(0L);
        return response;
    }
}