package com.example.fitness.services;
import com.example.fitness.entitties.Workouts;
import com.example.fitness.repositories.WorksoutsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class WorkoutsService {
    private final WorksoutsRepository worksoutsRepository;
    public WorkoutsService(WorksoutsRepository worksoutsRepository) {
        this.worksoutsRepository = worksoutsRepository;
    }
    public List<Workouts> allDailyActivities() {
        List<Workouts> workoutsList = new ArrayList<>();
        worksoutsRepository.getAllWorkouts().forEach(workoutsList::add);
        return workoutsList;
    }
    public List<Workouts> fetchAll(){
        return worksoutsRepository.getAllWorkouts();
    }
    public void addDailyWorkouts(Integer user_id, String type, Integer duration, Float calories_burned) {
        worksoutsRepository.addworkouts(user_id,type,duration,calories_burned);
    }
    public void deleteWorksOutsById(Integer id){
        worksoutsRepository.deleteWorkoutsByWorkout_id(id);
    }
    public List<Workouts> fetchWorkoutsByday(String created_at,Integer user_id) {
      return worksoutsRepository.findWorkoutsByday(created_at,user_id);
    };
    public List<Workouts> fetchWorkoutsByweek(String created_at,Integer user_id) {
        return worksoutsRepository.findWorkoutsByweek(created_at,user_id);
    };
}
