import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { WeatherResponse } from '../models/weather-response';
import { CacheStats } from '../models/cache-stats';
import { ErrorResponse } from '../models/error-response';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  private readonly API_URL = 'http://localhost:8080/api/weather';

  constructor(private http: HttpClient) { }

  /**
   * Fetches weather data for a given city
   * @param city - City name
   * @returns Observable of WeatherResponse
   */
  getWeather(city: string): Observable<WeatherResponse> {
    const params = new HttpParams().set('city', city.trim());
    
    return this.http.get<WeatherResponse>(this.API_URL, { params }).pipe(
      retry(1), // Retry once on failure
      catchError(this.handleError)
    );
  }

  /**
   * Fetches cache statistics
   * @returns Observable of CacheStats
   */
  getCacheStats(): Observable<CacheStats> {
    return this.http.get<CacheStats>(`${this.API_URL}/cache/stats`).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Handles HTTP errors
   * @param error - HttpErrorResponse
   * @returns Observable that errors with user-friendly message
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Network error: ${error.error.message}`;
    } else {
      // Backend returned an error response
      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else if (error.status === 404) {
        errorMessage = 'City not found. Please check the spelling and try again.';
      } else if (error.status === 400) {
        errorMessage = 'Invalid request. Please enter a city name.';
      } else if (error.status === 502 || error.status === 503) {
        errorMessage = 'Weather service is temporarily unavailable. Please try again later.';
      } else if (error.status === 0) {
        errorMessage = 'Cannot connect to weather service. Please ensure the backend is running.';
      } else {
        errorMessage = `Error: ${error.message}`;
      }
    }

    console.error('Weather Service Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}
