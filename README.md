# Weather Search Application

A full-stack weather search application with intelligent caching for improved performance.

## 🌟 Features

- **Real-time Weather Search**: Search for any city worldwide
- **Rich Weather Information**: Temperature, humidity, wind speed, sunrise/sunset, and more
- **Intelligent Caching**: Cached responses for faster repeated queries
- **Cache Performance Monitoring**: View cache hit/miss statistics
- **Beautiful UI**: Clean, minimal, Apple-inspired design
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile

## 🛠️ Tech Stack

### Backend
- **Java 17** with **Spring Boot 3.2.1**
- **Caffeine Cache** for high-performance caching
- **WebClient** for reactive API calls
- **Maven** for dependency management

### Frontend
- **Angular 17** (Standalone Components)
- **TypeScript**
- **CSS3** with Glassmorphism effects
- **RxJS** for reactive programming

### API
- **OpenWeatherMap API** (https://openweathermap.org/current)

## 📋 Prerequisites

- Java 17 or higher
- Node.js 18+ and npm
- Maven 3.6+
- Angular CLI (`npm install -g @angular/cli`)
- OpenWeatherMap API Key (free tier)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/weather-search-application.git
cd weather-search-application
```

### 2. Backend Setup
```bash
# Navigate to backend folder
cd backend

# Add your OpenWeatherMap API key
# Edit src/main/resources/application.properties
# Replace: openweather.api.key=YOUR_API_KEY_HERE

# Build and run
mvn clean install
mvn spring-boot:run
```

Backend will start at: **http://localhost:8080**

### 3. Frontend Setup
```bash
# Open new terminal
# Navigate to frontend folder
cd frontend

# Install dependencies
npm install

# Run development server
ng serve
```

Frontend will start at: **http://localhost:4200**

## 📡 API Documentation

### Endpoints

#### 1. Get Weather by City
```
GET /api/weather?city={cityName}
```

**Example Request:**
```bash
curl "http://localhost:8080/api/weather?city=London"
```

**Example Response:**
```json
{
  "cityName": "London",
  "country": "GB",
  "temperature": 15.5,
  "feelsLike": 14.2,
  "tempMin": 13.0,
  "tempMax": 17.0,
  "humidity": 72,
  "pressure": 1013,
  "weatherMain": "Clouds",
  "weatherDescription": "broken clouds",
  "weatherIcon": "04d",
  "windSpeed": 3.5,
  "windDegree": 220,
  "cloudiness": 75,
  "visibility": 10000,
  "sunrise": 1703226789,
  "sunset": 1703256123,
  "latitude": 51.51,
  "longitude": -0.13,
  "timestamp": "2024-12-23T10:30:00",
  "fromCache": false
}
```

**Error Response (404):**
```json
{
  "timestamp": "2024-12-23T10:30:00",
  "status": 404,
  "error": "City Not Found",
  "message": "City not found: InvalidCity",
  "path": "/api/weather"
}
```

#### 2. Get Cache Statistics
```
GET /api/weather/cache/stats
```

**Example Response:**
```json
{
  "hitCount": 5,
  "missCount": 3,
  "hitRate": 0.625,
  "missRate": 0.375,
  "hitRatePercentage": "62.50%",
  "missRatePercentage": "37.50%",
  "estimatedSize": 3,
  "evictionCount": 0
}
```

#### 3. Health Check
```
GET /api/weather/health
```

## 🎨 UI Features

- **Search Bar**: Intuitive search with autocomplete suggestions
- **Weather Card**: Beautiful card with glassmorphism effect
- **Temperature Display**: Large, easy-to-read temperature
- **Weather Details Grid**: 8 detailed weather metrics
- **Cache Indicator**: Shows when data is served from cache
- **Cache Stats Panel**: Toggle to view cache performance
- **Loading States**: Smooth loading animations
- **Error Handling**: User-friendly error messages

## ⚡ Caching Strategy

### Configuration
- **Cache Provider**: Caffeine Cache
- **Max Cache Size**: 100 entries
- **Cache Expiry**: 10 minutes after write
- **Cache Key**: City name (case-insensitive)

### Benefits
- **Reduced API Calls**: Cached responses for repeated queries
- **Faster Response Time**: Sub-millisecond response for cached data
- **Cost Optimization**: Fewer external API calls
- **Performance Metrics**: Built-in cache statistics

### Implementation Details
```java
@Cacheable(value = "weatherCache", key = "#cityName.toLowerCase().trim()")
public WeatherResponse getCurrentWeather(String cityName) {
    // Implementation
}
```

## 📁 Project Structure
```
weather-search-application/
├── backend/
│   └── src/main/java/com/rohit/weathersearch/
│       ├── config/              # Configuration classes
│       ├── controller/          # REST controllers
│       ├── service/             # Business logic
│       ├── model/               # DTOs and entities
│       └── exception/           # Exception handling
├── frontend/
│   └── src/app/
│       ├── components/          # UI components
│       ├── services/            # Angular services
│       └── models/              # TypeScript interfaces
└── README.md
```

## 🧪 Testing

### Backend Testing
```bash
# Test with curl
curl "http://localhost:8080/api/weather?city=Mumbai"

# Test cache (call same city twice)
curl "http://localhost:8080/api/weather?city=Mumbai"

# Check cache stats
curl "http://localhost:8080/api/weather/cache/stats"
```

### Frontend Testing
1. Open http://localhost:4200
2. Search for "London"
3. Verify weather data displays
4. Search "London" again - should show "From Cache" badge
5. Click "Show Cache Stats" to verify cache performance

## 🔧 Configuration

### Backend Configuration (application.properties)
```properties
# OpenWeatherMap API
openweather.api.key=YOUR_API_KEY
openweather.api.url=https://api.openweathermap.org/data/2.5/weather
openweather.api.timeout=5000

# Cache Configuration
cache.max-size=100
cache.expire-after-write-minutes=10

# Server
server.port=8080

# CORS
cors.allowed-origins=http://localhost:4200
```

## 🚧 Error Handling

The application handles various error scenarios:
- **City Not Found (404)**: Invalid city name
- **Bad Request (400)**: Missing or invalid parameters
- **Service Unavailable (503)**: External API down
- **Network Error (0)**: Backend not running

## 🎯 Performance Considerations

1. **Caching**: Reduces API calls by ~60-70% for common queries
2. **Lazy Loading**: Components loaded on demand
3. **Optimized API Calls**: Single request per search
4. **Debouncing**: (Can be added) Prevents excessive API calls
5. **Response Time**: 
   - First request: ~200-500ms (API call)
   - Cached request: <10ms

## 🔮 Future Enhancements

- [ ] 5-day weather forecast
- [ ] Weather alerts and notifications
- [ ] Favorite cities
- [ ] Search history
- [ ] Multiple city comparison
- [ ] Weather maps integration
- [ ] Dark mode support
- [ ] Geolocation-based weather
- [ ] Unit tests and integration tests

## 📄 License

This project is created for educational purposes.

## 👤 Author

**Rohit**

## 🙏 Acknowledgments

- OpenWeatherMap for providing weather data API
- Spring Boot and Angular communities
- Caffeine Cache for excellent caching library

---

**Note**: Remember to add your OpenWeatherMap API key in `backend/src/main/resources/application.properties` before running the application.
```

---
S_Store
Thumbs.db
