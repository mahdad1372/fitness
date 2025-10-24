package com.example.fitness.controllers;
import com.example.fitness.entitties.User;
import com.example.fitness.services.DailyActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequestMapping("/activity")
@RestController
public class DailyactivtiesController {
    private final DailyActivityService dailyactivityservice;
    public DailyactivtiesController(DailyActivityService dailyActivityService) {
        this.dailyactivityservice = dailyActivityService;
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/all")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.fetchAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/public-info")
    public String getPublicInfo() {
        return "This is a public API. No authentication required!";
    }

    // Public API 2: Another endpoint accessible without JWT
    @GetMapping("/about")
    public String getAboutInfo() {
        return "Welcome to the application! This is publicly available information.";
    }
    @PostMapping("/adduser")
    public void adduser(@RequestBody User users){
        userService.adduser(users.getFirstname(), users.getLastname(), users.getGender(), users.getEmail(),
                users.getPassword(),users.getWeight(),users.getHeight(),users.getBmi());
    }
    @DeleteMapping("/deleteuser")
    public void deleteuser(@RequestBody User users) {
        userService.deleteuserbyId(users.getUser_id());
    }
}
