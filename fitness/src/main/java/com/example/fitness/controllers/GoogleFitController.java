package com.example.fitness.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/google-fit")
public class GoogleFitController {

    // Endpoint to forward GET request to Google Fit heart rate dataset
    // The token is passed in the Authorization header
    @GetMapping("/heart-rate")
    public ResponseEntity<String> getHeartRateData(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization header missing or invalid. Use 'Bearer <access_token>'.");
        }

        String accessToken = authorizationHeader.substring("Bearer ".length());

        String googleFitUrl =
                "https://www.googleapis.com/fitness/v1/users/me/dataSources/" +
                        "derived:com.google.heart_rate.bpm:com.google.android.gms:merge_heart_rate_bpm/" +
                        "datasets/1731110400000000000-1731196800000000000";

        URI uri = UriComponentsBuilder.fromHttpUrl(googleFitUrl)
                .build(true) // do NOT encode the colons
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    // Get nutrition like the calories,fat,protein,carbs
    // --- Nutrition endpoint ---
    @GetMapping("/nutrition")
    public ResponseEntity<String> getNutrition(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization header missing or invalid. Use 'Bearer <access_token>'.");
        }

        String accessToken = authorizationHeader.substring("Bearer ".length());

        // IMPORTANT: Your original datasourceId MUST NOT BE ENCODED
        String googleFitUrl =
                "https://www.googleapis.com/fitness/v1/users/me/dataSources/" +
                        "raw:com.google.nutrition:428801282059:my-nutrition-source/" +
                        "datasets/1731110400000000000-1731196800000000000";

        URI uri = UriComponentsBuilder.fromHttpUrl(googleFitUrl)
                .build(true) // <-- DO NOT ENCODE COLONS
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }

    @GetMapping("/blood_pressure")
    public ResponseEntity<String> getBloodPressure(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization header missing or invalid. Use 'Bearer <access_token>'.");
        }

        String accessToken = authorizationHeader.substring("Bearer ".length());

        // IMPORTANT: Your original datasourceId MUST NOT BE ENCODED
        String googleFitUrl =
                "https://www.googleapis.com/fitness/v1/users/me/dataSources" +
                        "/raw:com.google.blood_pressure:428801282059:Custom:BP_Device:bp-002:my-bp-source" +
                        "/datasets/1731110400000000000-1731196800000000000";

        URI uri = UriComponentsBuilder.fromHttpUrl(googleFitUrl)
                .build(true) // <-- DO NOT ENCODE COLONS
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
    }
}