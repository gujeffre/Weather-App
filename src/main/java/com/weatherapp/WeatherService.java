package com.weatherapp;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WeatherService {
    private static final String API_KEY = System.getenv("OPENWEATHER_API_KEY");
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public WeatherService() {
        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IllegalStateException(
                "CRITICAL SECURITY EXCEPTION: The 'OPENWEATHER_API_KEY' environment variable is missing."
            );
        }
    }

    public String fetchRawWeatherData(String city) throws Exception {
        if (city == null || city.strip().isEmpty()) {
            throw new IllegalArgumentException("Input Error: City query parameter cannot be null or empty.");
        }

        String encodedCity = URLEncoder.encode(city.strip(), StandardCharsets.UTF_8);
        String finalUrl = String.format(BASE_URL, encodedCity, API_KEY);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(finalUrl))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            return response.body();
        } else if (statusCode == 404) {
            throw new IllegalArgumentException("The requested location could not be found.");
        } else if (statusCode == 401) {
            throw new SecurityException("API Authentication Failure: The current security token is invalid.");
        } else {
            throw new RuntimeException("External Gateway Error: Server answered with HTTP code " + statusCode);
        }
    }
}
