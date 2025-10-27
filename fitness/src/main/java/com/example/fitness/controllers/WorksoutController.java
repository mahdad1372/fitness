package com.example.fitness.controllers;
import com.example.fitness.entitties.Workouts;
import com.example.fitness.services.Complexlogic;
import com.example.fitness.services.WorkoutsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RequestMapping("/worksout")
@RestController
public class WorksoutController {
    private final WorkoutsService workoutsService;
    private Complexlogic complexlogic;
    public WorksoutController(WorkoutsService workoutsService, Complexlogic complexlogic) {
        this.workoutsService = workoutsService;
        this.complexlogic = complexlogic;
    }
    @GetMapping("/all")
        public ResponseEntity<List<Workouts>> allDailyActivities() {
        List<Workouts> dailyworksout = workoutsService.fetchAll();
        return ResponseEntity.ok(dailyworksout);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<Workouts>> DailyActivities(@RequestBody Workouts workouts) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<Workouts> dailyworksout = workoutsService.fetchWorkoutsByday(
                formatter.format(workouts.getCreatedAt()),workouts.getUser_id());
        List<Workouts> weekworksout = workoutsService.fetchWorkoutsByweek(
                formatter.format(workouts.getCreatedAt()),workouts.getUser_id());
        Integer totalWorkouts = dailyworksout.size();
        Integer workoutsThisWeek = weekworksout.size();
        float totalDuration = 0;
        float totalCalories = 0;
        for (Workouts w : dailyworksout) {
            totalCalories += w.getCalories_burned();
        }
        for (Workouts w : dailyworksout) {
            totalDuration += w.getDuration();
        }
        Float performancescore = complexlogic.performance(totalDuration,totalWorkouts,totalCalories,workoutsThisWeek);

        System.out.println(performancescore);
        return ResponseEntity.ok(dailyworksout);
    }


    @PostMapping("/addworksout")
    public void addworksout(@RequestBody Workouts workouts){
        workoutsService.addDailyWorkouts(workouts.getUser_id(),workouts.getType(),workouts.getDuration(),
                workouts.getCalories_burned());
    }

    @DeleteMapping("/deleteactivity/{id}")
    public void deleteworksoutById(@PathVariable("id") Integer id) {
        workoutsService.deleteWorksOutsById(id);
    }
}
