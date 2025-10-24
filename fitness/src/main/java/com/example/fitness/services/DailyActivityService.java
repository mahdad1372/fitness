package com.example.fitness.services;
import com.example.fitness.entitties.Daily_activities;
import com.example.fitness.repositories.DailyactivityRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class DailyActivityService {
    private final DailyactivityRepository dailyactivityRepository;
    public DailyActivityService(DailyactivityRepository dailyactivityRepository) {
        this.dailyactivityRepository = dailyactivityRepository;
    }
    public List<Daily_activities> allDailyActivities() {
        List<Daily_activities> dailyActivities = new ArrayList<>();
        dailyactivityRepository.findAll().forEach(dailyActivities::add);
        return dailyActivities;
    }
    public List<Daily_activities> fetchAll(){
        return dailyactivityRepository.getaAllDailyActivities();
    }
    public void addDailyActivities(Integer user_id, Date date, Integer steps, Float sleep_hours, DecimalFormat water_intake
            , DecimalFormat calories_burned, String mood, String notes) {
        dailyactivityRepository.addDailyActivity(user_id,date,steps,sleep_hours,water_intake,
               calories_burned,mood,notes);
    }
    public void deleteActivitiesById(Integer id){
        dailyactivityRepository.deleteDaily_activitiesById(id);
    }
}
