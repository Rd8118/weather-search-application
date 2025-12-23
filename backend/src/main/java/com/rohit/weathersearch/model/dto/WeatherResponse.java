package com.rohit.weathersearch.model.dto;

import java.time.LocalDateTime;

/**
 * DTO sent to frontend with formatted weather information.
 * Contains only the essential data needed by the UI.
 */
public class WeatherResponse {
    
    private String cityName;
    private String country;
    private Double temperature;
    private Double feelsLike;
    private Double tempMin;
    private Double tempMax;
    private Integer humidity;
    private Integer pressure;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;
    private Double windSpeed;
    private Integer windDegree;
    private Integer cloudiness;
    private Integer visibility;
    private Long sunrise;
    private Long sunset;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private Boolean fromCache;
    
    // Constructors
    public WeatherResponse() {
    }
    
    // Helper method
    public String getWeatherIconUrl() {
        if (weatherIcon != null) {
            return "https://openweathermap.org/img/wn/" + weatherIcon + "@2x.png";
        }
        return null;
    }
    
    // Getters and Setters
    public String getCityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public Double getFeelsLike() {
        return feelsLike;
    }
    
    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }
    
    public Double getTempMin() {
        return tempMin;
    }
    
    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }
    
    public Double getTempMax() {
        return tempMax;
    }
    
    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }
    
    public Integer getHumidity() {
        return humidity;
    }
    
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
    
    public Integer getPressure() {
        return pressure;
    }
    
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }
    
    public String getWeatherMain() {
        return weatherMain;
    }
    
    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }
    
    public String getWeatherDescription() {
        return weatherDescription;
    }
    
    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
    
    public String getWeatherIcon() {
        return weatherIcon;
    }
    
    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
    
    public Double getWindSpeed() {
        return windSpeed;
    }
    
    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
    
    public Integer getWindDegree() {
        return windDegree;
    }
    
    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }
    
    public Integer getCloudiness() {
        return cloudiness;
    }
    
    public void setCloudiness(Integer cloudiness) {
        this.cloudiness = cloudiness;
    }
    
    public Integer getVisibility() {
        return visibility;
    }
    
    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
    
    public Long getSunrise() {
        return sunrise;
    }
    
    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }
    
    public Long getSunset() {
        return sunset;
    }
    
    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Boolean getFromCache() {
        return fromCache;
    }
    
    public void setFromCache(Boolean fromCache) {
        this.fromCache = fromCache;
    }
}