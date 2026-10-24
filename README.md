# Secure Asynchronous Weather Dashboard

A production-ready, modular desktop application built in Java 17 and JavaFX. This project serves as a portfolio piece showcasing clean architecture, secure credential isolation, asynchronous multi-threading, and automated unit testing methodologies. It accepts a city name, connects to a weather API, parses the JSON response, and displays live data and the weather forecast for the input city.

## Key Features
- Zero-Credential Exposure (`System.getenv`)
- Input Sanitization
- Asynchronous Daemon Threading for responsive UI
- Mock Data Offline Testing via JUnit 5
