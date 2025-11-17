package com.example.fitness.services;
import com.example.fitness.repositories.Health_metricsRepository;
import org.springframework.stereotype.Service;

@Service
public class Health_metricsService {
    private final Health_metricsRepository health_metricsRepository;
    public Health_metricsService(Health_metricsRepository health_metricsRepository) {
        this.health_metricsRepository = health_metricsRepository;
    }
    public void addHealth_metrics(Integer user_id, Float cholesterol, Float blood_pressure, Float heart_rate) {
        health_metricsRepository.addHealth_metrics(user_id, cholesterol, blood_pressure, heart_rate);

    }
}
