package com.connormorales.weather_dashboard.service;

import com.connormorales.weather_dashboard.entity.WeatherData;
import com.connormorales.weather_dashboard.exception.WeatherNotFoundException;
import com.connormorales.weather_dashboard.model.WeatherResponse;
import com.connormorales.weather_dashboard.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class WeatherService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ErrorLogService errorLogService;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String baseUrl;

    @Value("${weather.api.units}")
    private String units;

    public WeatherResponse getWeather(String city){
        String url = String.format("%s?q=%s&appid=%s&units=%s", baseUrl, city, apiKey, units);
        try {
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            if (response == null || response.getWeather() == null) {
                throw new WeatherNotFoundException("Weather data not found for city: " + city);
            }

            saveWeatherData(response);

            return response;
        } catch (HttpClientErrorException.NotFound ex) {
            errorLogService.logError(ex);
            throw new WeatherNotFoundException("City not found: " + city);
        } catch (Exception ex) {
            errorLogService.logError(ex);
            throw new RuntimeException("Failed to fetch weather data: " + ex.getMessage());
        }
    }

    private void saveWeatherData(WeatherResponse response) {
        if (response == null || response.getWeather() == null) return;

        WeatherData data = WeatherData.builder()
                .city(response.getName())
                .temperature(response.getMain().getTemp())
                .humidity(response.getMain().getHumidity())
                .pressure(response.getMain().getPressure())
                .windSpeed(response.getWind().getSpeed())
                .weatherMain(response.getWeather().get(0).getMain())
                .weatherDescription(response.getWeather().get(0).getDescription())
                .timestamp(LocalDateTime.now())
                .build();

        weatherDataRepository.save(data);
    }

    public WeatherResponse fetchWeather(String city) {
        return new WeatherResponse();
    }
}

