package com.weatherapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherParserTest {

    private WeatherParser parser;

    @BeforeEach
    void setUp() {
        parser = new WeatherParser();
    }

    @Test
    void testSuccessfulParsing() throws Exception {
        String mockJson = "{\"name\": \"Detroit\", \"main\": {\"temp\": 14.5, \"humidity\": 62}, \"weather\": [{\"description\": \"scattered clouds\"}]}";

        WeatherData data = parser.parse(mockJson);

        assertNotNull(data);
        assertEquals("Detroit", data.cityName());
        assertEquals(14.5, data.temperature(), 0.01);
        assertEquals(62, data.humidity());
        assertEquals("scattered clouds", data.conditions());
    }
}
