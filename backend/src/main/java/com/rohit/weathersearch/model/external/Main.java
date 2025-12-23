package com.rohit.weathersearch.model.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Main {

    private Double temp;
    
    @JsonProperty("feels_like")  // ← ADD THIS
    private Double feelsLike;
    
    @JsonProperty("temp_min")    // ← ADD THIS
    private Double tempMin;
    
    @JsonProperty("temp_max")    // ← ADD THIS
    private Double tempMax;
    
    private Integer humidity;
    private Integer pressure;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
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
}