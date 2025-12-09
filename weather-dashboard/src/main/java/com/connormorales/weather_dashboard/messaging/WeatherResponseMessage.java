package com.connormorales.weather_dashboard.messaging;

    public class WeatherResponseMessage {
        private String city;
        private String summary;
        private double temperatureC;

        public WeatherResponseMessage() {}

        public WeatherResponseMessage(String city, String summary, double temperatureC) {
            this.city = city;
            this.summary = summary;
            this.temperatureC = temperatureC;
        }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        public double getTemperatureC() { return temperatureC; }
        public void setTemperatureC(double temperatureC) { this.temperatureC = temperatureC; }

}
