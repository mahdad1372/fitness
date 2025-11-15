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

    public Double Cardiovascular(Integer age, Float bloodPressure,Float TotalCholestrol,Float HdlCholestrol,String sex,
                                Integer Heartrate,
                                Integer smoker) {
        double cardiovascular;
        if (sex.equalsIgnoreCase("male")) {
            cardiovascular =
                    0.04826 * age +
                            1.600 * TotalCholestrol -
                            0.523 * HdlCholestrol +
                            1.148 * bloodPressure +
                            0.428 * smoker;

            // Heart rate adjustment (custom)
            double hrFactor = 0.015 * (Heartrate - 70);
            cardiovascular += hrFactor;

            return 1 - Math.pow(0.88936, Math.exp(cardiovascular - 23.9802));
        }         // --------------------------
        // WOMEN FORMULA
        // --------------------------
        else {
            cardiovascular =
                    0.33766 * age +
                            0.26138 * TotalCholestrol -
                            0.7181 * HdlCholestrol +
                            2.81291 * bloodPressure +
                            0.52873 * smoker;

            // Heart rate adjustment (custom)
            double hrFactor = 0.015 * (Heartrate - 70);
            cardiovascular += hrFactor;

            return 1 - Math.pow(0.88936, Math.exp(cardiovascular - 23.9802));
        }
    }

}
