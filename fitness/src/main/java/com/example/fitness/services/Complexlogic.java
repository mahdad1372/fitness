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
}
