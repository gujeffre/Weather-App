package com.weatherapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherData parse(String rawJson) throws Exception {
        JsonNode root = mapper.readTree(rawJson);

        String cityName = root.path("name").asText("Unknown Location");
        double temperature = root.path("main").path("temp").asDouble(0.0);
        int humidity = root.path("main").path("humidity").asInt(0);
        
        String description = "Unknown Conditions";
        JsonNode weatherArray = root.path("weather");
        if (weatherArray.isArray() && !weatherArray.isEmpty()) {
            description = weatherArray.get(0).path("description").asText("Unknown");
        }

        return new WeatherData(cityName, temperature, description, humidity);
    }
}
