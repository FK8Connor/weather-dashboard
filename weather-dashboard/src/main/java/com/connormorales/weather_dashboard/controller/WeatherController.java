package com.connormorales.weather_dashboard.controller;

import com.connormorales.weather_dashboard.entity.ErrorLog;
import com.connormorales.weather_dashboard.entity.WeatherData;
import com.connormorales.weather_dashboard.model.WeatherResponse;
import com.connormorales.weather_dashboard.repository.ErrorLogRepository;
import com.connormorales.weather_dashboard.repository.WeatherDataRepository;
import com.connormorales.weather_dashboard.service.WeatherService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private ErrorLogRepository errorLogRepository;

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

    @GetMapping("/errors")
    public List<ErrorLog> getErrorLogs(){
        return errorLogRepository.findAll();
    }
}
