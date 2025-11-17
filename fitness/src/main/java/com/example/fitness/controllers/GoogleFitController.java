package com.example.fitness.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/google-fit")
public class GoogleFitController {


    private float calculateAverageHeartRate(JsonNode root) {
        JsonNode points = root.path("point");

        if (!points.isArray() || points.isEmpty()) {
            return 0;
        }

        float sum = 0;
        int count = 0;

        for (JsonNode point : points) {
            JsonNode valueArray = point.path("value");

            if (valueArray.isArray() && !valueArray.isEmpty()) {
                float bpm = (float) valueArray.get(0).path("fpVal").asDouble();
                sum += bpm;
                count++;
            }
        }

        return (count == 0) ? 0 : sum / count;
    }

    // ---------------- Endpoint ----------------
    @GetMapping("/heart-rate")
    public ResponseEntity<?> getHeartRateData(
            @RequestHeader("Authorization") String authorizationHeader) {

        // Validate Authorization header
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
                .build(true) // keeps colons unencoded
                .toUri();

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // Fetch from Google Fit
            ResponseEntity<String> response =
                    restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // Calculate average
            float average = calculateAverageHeartRate(root);

            // Return ONLY the number
            return ResponseEntity.ok(average);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching or processing heart rate data: " + e.getMessage());
        }
    }

    // Get nutrition like the calories,fat,protein,carbs
    // --- Nutrition endpoint ---
    @GetMapping("/nutrition")
    public ResponseEntity<?> getNutrition(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization header missing or invalid. Use 'Bearer <access_token>'.");
        }

        String accessToken = authorizationHeader.substring("Bearer ".length());

        String googleFitUrl =
                "https://www.googleapis.com/fitness/v1/users/me/dataSources/" +
                        "raw:com.google.nutrition:428801282059:my-nutrition-source/" +
                        "datasets/1731110400000000000-1731196800000000000";

        URI uri = UriComponentsBuilder.fromHttpUrl(googleFitUrl)
                .build(true)
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        // ---- Extract calories from JSON ----
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            JsonNode points = root.path("point");
            if (points.isMissingNode() || !points.isArray() || points.isEmpty()) {
                return ResponseEntity.ok(Map.of("calories", 0));
            }

            JsonNode valueArray = points.get(0).path("value");
            float calories = 0;

            // value[0] contains mapVal (nutrients)
            JsonNode nutrients = valueArray.get(0).path("mapVal");

            for (JsonNode item : nutrients) {
                if (item.path("key").asText().equals("calories")) {
                    calories = (float) item.path("value").path("fpVal").asDouble();
                    break;
                }
            }

            return ResponseEntity.ok(calories);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error parsing Google Fit response: " + e.getMessage());
        }
    }

    @GetMapping("/blood_pressure")
    public Integer getSystolicBloodPressure(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header missing or invalid.");
        }

        String accessToken = authorizationHeader.substring("Bearer ".length());

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

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            List<Integer> systolicValues = new ArrayList<>();

            if (root.has("point")) {
                for (JsonNode point : root.get("point")) {
                    JsonNode valueArray = point.get("value");
                    if (valueArray != null && valueArray.size() > 0) {
                        JsonNode systolicNode = valueArray.get(0); // first value = systolic
                        if (systolicNode.has("fpVal")) {
                            systolicValues.add((int) Math.round(systolicNode.get("fpVal").asDouble()));
                        }
                    }
                }
            }

            // Return average systolic BP as single Integer
            if (systolicValues.isEmpty()) {
                return 0; // or null if you prefer
            }

            int sum = systolicValues.stream().mapToInt(Integer::intValue).sum();
            return sum / systolicValues.size();

        } catch (Exception e) {
            e.printStackTrace();
            return 0; // or throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}