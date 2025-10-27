package com.example.fitness.controllers;
import com.example.fitness.entitties.Workouts;
import com.example.fitness.services.WorkoutsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RequestMapping("/worksout")
@RestController
public class WorksoutController {
    private final WorkoutsService workoutsService;
    public WorksoutController(WorkoutsService workoutsService) {
        this.workoutsService = workoutsService;
    }
    @GetMapping("/all")
        public ResponseEntity<List<Workouts>> allDailyActivities() {
        List<Workouts> dailyworksout = workoutsService.fetchAll();
        return ResponseEntity.ok(dailyworksout);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<Workouts>> DailyActivities(@RequestBody Workouts workouts) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatter.format(workouts.getCreatedAt())); //
        List<Workouts> dailyworksout = workoutsService.fetchWorkoutsByday(
                formatter.format(workouts.getCreatedAt()));
        System.out.println(workouts.getCreatedAt());
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
