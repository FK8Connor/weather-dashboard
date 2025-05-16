package com.connormorales.weather_dashboard.controller;

import com.connormorales.weather_dashboard.entity.WeatherData;
import com.connormorales.weather_dashboard.model.WeatherResponse;
import com.connormorales.weather_dashboard.repository.WeatherDataRepository;
import com.connormorales.weather_dashboard.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city) {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty");
        }
        return weatherService.getWeather(city);
    }

    @GetMapping("/history")
    public List<WeatherData> getWeatherHistory(){
        return weatherDataRepository.findAll();
    }
}
