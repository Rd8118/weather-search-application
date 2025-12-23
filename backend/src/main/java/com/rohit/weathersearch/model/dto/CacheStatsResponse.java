package com.rohit.weathersearch.model.dto;

/**
 * DTO for cache statistics.
 * Provides insights into cache performance.
 */
public class CacheStatsResponse {
    
    private Long hitCount;
    private Long missCount;
    private Long loadSuccessCount;
    private Long loadFailureCount;
    private Long totalLoadTime;
    private Long evictionCount;
    private Double hitRate;
    private Double missRate;
    private Long estimatedSize;
    
    // Constructors
    public CacheStatsResponse() {
    }
    
    public CacheStatsResponse(Long hitCount, Long missCount, Long loadSuccessCount,
                             Long loadFailureCount, Long totalLoadTime, Long evictionCount,
                             Double hitRate, Double missRate, Long estimatedSize) {
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.loadSuccessCount = loadSuccessCount;
        this.loadFailureCount = loadFailureCount;
        this.totalLoadTime = totalLoadTime;
        this.evictionCount = evictionCount;
        this.hitRate = hitRate;
        this.missRate = missRate;
        this.estimatedSize = estimatedSize;
    }
    
    // Helper methods
    public String getHitRatePercentage() {
        return String.format("%.2f%%", hitRate * 100);
    }
    
    public String getMissRatePercentage() {
        return String.format("%.2f%%", missRate * 100);
    }
    
    // Getters and Setters
    public Long getHitCount() {
        return hitCount;
    }
    
    public void setHitCount(Long hitCount) {
        this.hitCount = hitCount;
    }
    
    public Long getMissCount() {
        return missCount;
    }
    
    public void setMissCount(Long missCount) {
        this.missCount = missCount;
    }
    
    public Long getLoadSuccessCount() {
        return loadSuccessCount;
    }
    
    public void setLoadSuccessCount(Long loadSuccessCount) {
        this.loadSuccessCount = loadSuccessCount;
    }
    
    public Long getLoadFailureCount() {
        return loadFailureCount;
    }
    
    public void setLoadFailureCount(Long loadFailureCount) {
        this.loadFailureCount = loadFailureCount;
    }
    
    public Long getTotalLoadTime() {
        return totalLoadTime;
    }
    
    public void setTotalLoadTime(Long totalLoadTime) {
        this.totalLoadTime = totalLoadTime;
    }
    
    public Long getEvictionCount() {
        return evictionCount;
    }
    
    public void setEvictionCount(Long evictionCount) {
        this.evictionCount = evictionCount;
    }
    
    public Double getHitRate() {
        return hitRate;
    }
    
    public void setHitRate(Double hitRate) {
        this.hitRate = hitRate;
    }
    
    public Double getMissRate() {
        return missRate;
    }
    
    public void setMissRate(Double missRate) {
        this.missRate = missRate;
    }
    
    public Long getEstimatedSize() {
        return estimatedSize;
    }
    
    public void setEstimatedSize(Long estimatedSize) {
        this.estimatedSize = estimatedSize;
    }
}