package com.example.fitness.controllers;
import com.example.fitness.dto.CardiovascularRequest;
import com.example.fitness.entitties.Health_metrics;
import com.example.fitness.services.Complexlogic;
import com.example.fitness.services.Health_metricsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health_metric")
public class Health_metricsController {
    private final Health_metricsService health_metricsService;
    private final Complexlogic complexlogic;
    public Health_metricsController(Health_metricsService health_metricsService, Complexlogic complexlogic) {
        this.health_metricsService = health_metricsService;
        this.complexlogic = complexlogic;
    }
    @PostMapping("/addmetrics")
    public void addhealth_metrics(@RequestBody Health_metrics health_metrics){
        health_metricsService.addHealth_metrics(health_metrics.getUser_id(),health_metrics.getCholesterol(),
                health_metrics.getBlood_pressure(),health_metrics.getHeart_rate());
    }
    @PostMapping("/cardiovascular")
    public double getCardiovascular(@RequestBody CardiovascularRequest dto){
      return complexlogic.cardiovascular(dto.age,dto.bloodPressure,dto.cholesterol,dto.sex,
              dto.heartRate,dto.smoker);
    }
}
