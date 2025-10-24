package com.example.fitness.services;
import com.example.fitness.entitties.User;
import com.example.fitness.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    public List<User> fetchAll(){
        return userRepository.getAllUsers();
    }
    public void adduser(Integer id,Float bmi,String email,String firstname,String gender,Float height
            ,String lastname,String password,Float weight) {
        userRepository.addUser(id,bmi,email,firstname,gender,height,lastname,password,weight);

    }
    public void deleteuserbyId(Integer id){
        userRepository.deleteUsersById(id);
    }
}
