import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { SearchBar  } from './components/search-bar/search-bar';
import { WeatherDisplay } from './components/weather-display/weather-display';
import { LoadingSpinner } from './components/loading-spinner/loading-spinner';
import { WeatherService } from './services/weather';
import { WeatherResponse } from './models/weather-response';
import { CacheStats } from './models/cache-stats';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    SearchBar,
    WeatherDisplay,
    LoadingSpinner
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  title = 'Weather Search Application';
  
  weatherData: WeatherResponse | null = null;
  cacheStats: CacheStats | null = null;
  isLoading: boolean = false;
  errorMessage: string = '';
  showCacheStats: boolean = false;

  constructor(private weatherService: WeatherService) {}

  /**
   * Handles city search from search bar component
   */
  onSearchCity(city: string): void {
    this.errorMessage = '';
    this.isLoading = true;
    this.weatherData = null;

    this.weatherService.getWeather(city).subscribe({
      next: (data) => {
        this.weatherData = data;
        this.isLoading = false;
        console.log('Weather data received:', data);
        
        // Load cache stats after successful search
        this.loadCacheStats();
      },
      error: (error) => {
        this.errorMessage = error.message;
        this.isLoading = false;
        console.error('Error fetching weather:', error);
      }
    });
  }

  /**
   * Loads cache statistics
   */
  loadCacheStats(): void {
    this.weatherService.getCacheStats().subscribe({
      next: (stats) => {
        this.cacheStats = stats;
        console.log('Cache stats:', stats);
      },
      error: (error) => {
        console.error('Error fetching cache stats:', error);
      }
    });
  }

  /**
   * Toggles cache stats display
   */
  toggleCacheStats(): void {
    this.showCacheStats = !this.showCacheStats;
    if (this.showCacheStats && !this.cacheStats) {
      this.loadCacheStats();
    }
  }

  /**
   * Clears weather data and error message
   */
  clearData(): void {
    this.weatherData = null;
    this.errorMessage = '';
    this.cacheStats = null;
  }
}