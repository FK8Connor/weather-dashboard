package com.connormorales.weather_dashboard.controller;

import com.connormorales.weather_dashboard.messaging.WeatherResponseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/weather")
public class WeatherRequestController {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String inputRoutingKey;

    public WeatherRequestController(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbit.exchange}") String exchangeName,
            @Value("${app.rabbit.input-routing-key}") String inputRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.inputRoutingKey = inputRoutingKey;
    }

    @PostMapping("/request")
    public ResponseEntity<Void> publishRequest(@RequestBody WeatherResponseMessage request) {
        rabbitTemplate.convertAndSend(exchangeName, inputRoutingKey, request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
