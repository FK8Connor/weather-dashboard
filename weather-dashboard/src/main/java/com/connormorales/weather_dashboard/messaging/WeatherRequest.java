package com.connormorales.weather_dashboard.messaging;

public class WeatherRequest {
    private String city;

    public WeatherRequest() {}

    public WeatherRequest(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
