package com.connormorales.weather_dashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table( name = "weather_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private double temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;

    private String weatherMain;
    private String weatherDescription;

    private LocalDateTime timestamp;

}
