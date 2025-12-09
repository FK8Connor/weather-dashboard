package com.connormorales.weather_dashboard.messaging;

import com.connormorales.weather_dashboard.model.WeatherResponse;
import com.connormorales.weather_dashboard.service.WeatherService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherMessageListener {

    private final RabbitTemplate rabbitTemplate;
    private final WeatherService weatherService;
    private final String exchangeName;
    private final String outputRoutingKey;

    public WeatherMessageListener(
            RabbitTemplate rabbitTemplate,
            WeatherService weatherService,
            @Value("${app.rabbit.exchange}") String exchangeName,
            @Value("${app.rabbit.output-routing-key}") String outputRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.weatherService = weatherService;
        this.exchangeName = exchangeName;
        this.outputRoutingKey = outputRoutingKey;
    }

    @RabbitListener(queues = "${app.rabbit.input-queue}")
    public void handleWeatherRequest(WeatherRequest request) {
        WeatherResponse response = weatherService.getWeather(request.getCity());
        rabbitTemplate.convertAndSend(exchangeName, outputRoutingKey, response);
    }
}
