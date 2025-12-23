export interface CacheStats {
  hitCount: number;
  missCount: number;
  loadSuccessCount: number;
  loadFailureCount: number;
  totalLoadTime: number;
  evictionCount: number;
  hitRate: number;
  missRate: number;
  estimatedSize: number;
  hitRatePercentage: string;
  missRatePercentage: string;
}