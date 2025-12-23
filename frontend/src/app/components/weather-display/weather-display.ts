import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WeatherResponse } from '../../models/weather-response';

@Component({
  selector: 'app-weather-display',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './weather-display.html',
  styleUrls: ['./weather-display.css']
})
export class WeatherDisplay {
  @Input() weather: WeatherResponse | null = null;

  /**
   * Converts Unix timestamp to readable time format
   */
  formatTime(timestamp: number): string {
    return new Date(timestamp * 1000).toLocaleTimeString('en-US', {
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  /**
   * Converts wind degree to direction (N, NE, E, SE, S, SW, W, NW)
   */
  getWindDirection(degree: number): string {
    const directions = ['N', 'NE', 'E', 'SE', 'S', 'SW', 'W', 'NW'];
    const index = Math.round(degree / 45) % 8;
    return directions[index];
  }

  /**
   * Converts visibility from meters to kilometers
   */
  getVisibilityKm(visibility: number): string {
    return (visibility / 1000).toFixed(1);
  }

  /**
   * Gets background gradient based on weather condition
   */
  getWeatherBackground(): string {
    if (!this.weather) return 'default';
    
    const condition = this.weather.weatherMain.toLowerCase();
    
    if (condition.includes('clear')) return 'clear';
    if (condition.includes('cloud')) return 'cloudy';
    if (condition.includes('rain') || condition.includes('drizzle')) return 'rainy';
    if (condition.includes('snow')) return 'snowy';
    if (condition.includes('thunder')) return 'thunderstorm';
    if (condition.includes('mist') || condition.includes('fog')) return 'misty';
    
    return 'default';
  }

  /**
   * Rounds temperature to nearest integer
   */
  roundTemp(temp: number): number {
    return Math.round(temp);
  }
}