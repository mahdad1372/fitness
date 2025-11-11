package com.example.fitness.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

        // Extract the token from the header
        String accessToken = authorizationHeader.substring("Bearer ".length());

        String googleFitUrl =
                "https://www.googleapis.com/fitness/v1/users/me/dataSources/"
                        + "raw:com.google.heart_rate.bpm:428801282059:Google:Pixel_7:device-001:my-new-heart-source"
                        + "/datasets/1731110400000000000-1731196800000000000";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Attach OAuth token
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                googleFitUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}