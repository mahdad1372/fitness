package com.example.fitness.controllers;
import com.example.fitness.entitties.Health_metrics;
import com.example.fitness.services.Health_metricsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health_metric")
public class Health_metricsController {
    private final Health_metricsService health_metricsService;
    public Health_metricsController(Health_metricsService health_metricsService) {
        this.health_metricsService = health_metricsService;
    }
    @PostMapping("/addmetrics")
    public void addhealth_metrics(@RequestBody Health_metrics health_metrics){
        health_metricsService.addHealth_metrics(health_metrics.getUser_id(),health_metrics.getCholesterol(),
                health_metrics.getBlood_pressure(),health_metrics.getHeart_rate());

    }
}
