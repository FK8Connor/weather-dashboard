package com.connormorales.weather_dashboard.messaging;

public class WeatherRequestMessage {
    private String city;

    public WeatherRequestMessage() {}

    public WeatherRequestMessage(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
