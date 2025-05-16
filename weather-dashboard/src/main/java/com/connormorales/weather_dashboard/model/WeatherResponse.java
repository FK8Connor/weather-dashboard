package com.connormorales.weather_dashboard.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    private List<Weather> weather;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    private Main main;
    private Wind wind;
    private String name; //This will be the City Name


}
