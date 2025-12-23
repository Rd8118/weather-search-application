export interface WeatherResponse {
  cityName: string;
  country: string;
  temperature: number;
  feelsLike: number;
  tempMin: number;
  tempMax: number;
  humidity: number;
  pressure: number;
  weatherMain: string;
  weatherDescription: string;
  weatherIcon: string;
  windSpeed: number;
  windDegree: number;
  cloudiness: number;
  visibility: number;
  sunrise: number;
  sunset: number;
  latitude: number;
  longitude: number;
  timestamp: string;
  fromCache: boolean;
  weatherIconUrl?: string;
}
