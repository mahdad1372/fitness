package com.example.fitness.controllers;
import com.example.fitness.entitties.User;
import com.example.fitness.services.Complexlogic;
import com.example.fitness.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final Complexlogic complexlogic;
    private final UserService userService;

    public UserController(UserService userService, Complexlogic complexlogic) {
        this.complexlogic = complexlogic;
        this.userService = userService;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> allUsers() {
        // Ensure the user is authenticated
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
        // Fetch and return all users
        List<User> users = userService.fetchAll();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/getuserbyid")
    public ResponseEntity<List<User>> allUsers(@RequestBody Map<String, Integer> request) {
        Integer id = request.get("id");

        List<User> users = userService.finduserbyid(id);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/public-info")
    public String getPublicInfo() {
        return "This is a public API. No authentication required!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User users) {
        String email = users.getEmail();
        String password = users.getPassword();

        // Validate input
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
        }

        // Check user credentials using your UserService
        Optional<User> user = userService.finduserbyemail(email);

        if (user == null || !users.getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }

        // Login successful
        return ResponseEntity.ok(Map.of(
                "message", "Login successful"
        ));
    }

    // Public API 2: Another endpoint accessible without JWT
    @GetMapping("/about")
    public String getAboutInfo() {
        return "Welcome to the application! This is publicly available information.";
    }

    @PostMapping("/adduser")
    public void adduser(@RequestBody User users) {
        userService.adduser(users.getUser_id(), users.getEmail(),
                users.getFirstname(), users.getGender(), users.getHeight(), users.getLastname(),
                users.getPassword(), users.getWeight());
    }
}