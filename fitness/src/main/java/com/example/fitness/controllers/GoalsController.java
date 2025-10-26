package com.example.fitness.controllers;

import com.example.fitness.entitties.Daily_activities;
import com.example.fitness.entitties.Goals;
import com.example.fitness.services.DailyActivityService;
import com.example.fitness.services.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/goals")
@RestController
public class GoalsController {
    private final GoalService goalservice;
    public GoalsController(GoalService goalservice) {
        this.goalservice = goalservice;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Goals>> allDailyActivities() {
        List<Goals> goals = goalservice.fetchAll();
        return ResponseEntity.ok(goals);
    }
    @PostMapping("/add")
    public void addgoals(@RequestBody Goals goals){
        goalservice.addGoals(goals.getUser_id(),goals.getGoal_type(),goals.getTarget_value(),goals.getCurrent_value()
        ,goals.getStart_date(),goals.getEnd_date(),goals.getStatus());
    }

//    @DeleteMapping("/deleteactivity/{id}")
//    public void deleteadctivitybyId(@PathVariable("id") Integer id) {
//        dailyactivityservice.deleteActivitiesById(id);
//    }
}

