package com.example.fitness.services;
import com.example.fitness.entitties.User;
import com.example.fitness.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Complexlogic {
    private final UserRepository userRepository;
    public Complexlogic(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public String bmi(Integer id) {
        List<User> user = userRepository.findByUser_id(id);
        Float weight = user.get(0).getWeight();
        Float height = user.get(0).getHeight();
        Float bmi = weight / (height * height);
        String status;
        if (bmi < 18.5) status = "Underweight";
        else if (bmi < 25) status = "Normal weight";
        else if (bmi < 30) status = "Overweight";
        else if (bmi < 35) status = "Obese (Class I)";
        else if (bmi < 40) status = "Obese (Class II)";
        else status = "Obese (Class III)";
        return String.format("The BMI is : %.1f (%s)", bmi, status);
    }

    public String performance(Float totalDuration , Integer totalWorkouts,
                              Float totalCalories,Integer workoutsThisWeek) {

        Float avgDuration = (Float) totalDuration / (float) totalWorkouts;
        Float intensityFactor = (float) (totalCalories / totalDuration);
        Float consistency = (float) workoutsThisWeek / 5;
        Float performanceScore = (avgDuration * intensityFactor * consistency);

        String Status;

        if (performanceScore < 20) {
            Status = "Poor";
        } else if (performanceScore < 40) {
            Status = "Needs Improvement";
        } else if (performanceScore < 60) {
            Status = "Good";
        } else if (performanceScore < 80) {
            Status = "Very Good";
        } else {
            Status = "Excellent";
        }
        String massage = "The performance score is " + performanceScore + " and your performance is "
                + Status;
        return massage;
    }
    public String estimateHeartAttackRisk(double cardiovascularScore) {

        if (cardiovascularScore < 200) {
            return "Low risk";
        } else if (cardiovascularScore < 400) {
            return "Moderate risk";
        } else if (cardiovascularScore < 700) {
            return "High risk";
        } else {
            return "Very high risk";
        }
    }
    public double  cardiovascular(Integer age, Float bloodPressure, Float cholesterol,
                                 String sex, Float heartRate, Integer smoker) {

        // Estimate HDL from total cholesterol (you can change this assumption)
        double estimatedHdl = cholesterol * 0.20;

        double cardiovascular;

        // --------------------------
        // MALE FORMULA
        // --------------------------
        if (sex.equalsIgnoreCase("male")) {
            cardiovascular =
                    0.04826 * age +
                            1.600 * cholesterol -
                            0.523 * estimatedHdl +
                            1.148 * bloodPressure +
                            0.428 * smoker;

        } else {
            // --------------------------
            // FEMALE FORMULA
            // --------------------------
            cardiovascular =
                    0.33766 * age +
                            0.26138 * cholesterol -
                            0.7181 * estimatedHdl +
                            2.81291 * bloodPressure +
                            0.52873 * smoker;
        }

        // Heart rate factor (custom)
        cardiovascular += 0.015 * (heartRate - 70);
        // Final transformation
        return cardiovascular;
    }

}
