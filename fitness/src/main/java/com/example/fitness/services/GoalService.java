package com.example.fitness.services;

import com.example.fitness.entitties.Daily_activities;
import com.example.fitness.entitties.Goals;
import com.example.fitness.repositories.DailyactivityRepository;
import com.example.fitness.repositories.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class GoalService {
    private final GoalRepository Goalrepository;
    public GoalService(GoalRepository Goalrepository) {
        this.Goalrepository = Goalrepository;
    }
    public List<Goals> getAllGoals() {
        List<Goals> goals = new ArrayList<>();
        Goalrepository.getAllGoals().forEach(goals::add);
        return goals;
    }
    public List<Goals> fetchAll(){
        return Goalrepository.getAllGoals();
    }
    public void addGoals(Integer user_id, String goal_type, Float target_value, Float current_value
            , Date start_date, Date end_date, String status) {
        Goalrepository.addgoals(user_id,goal_type,target_value,current_value,start_date,end_date,status);
    }
    public void deleteActivitiesById(Integer id){
        Goalrepository.deleteGoalsByGoal_id(id);
    }
}
