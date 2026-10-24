package com.weatherapp;

public record WeatherData(
    String cityName, 
    double temperature, 
    String conditions, 
    int humidity
) {}
