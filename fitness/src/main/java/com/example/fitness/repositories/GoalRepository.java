package com.example.fitness.repositories;

import com.example.fitness.entitties.Foods;
import com.example.fitness.entitties.Goals;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends CrudRepository<Goals, Integer> {
    @Query(value="SELECT * FROM Goals a WHERE a.goal_id=?1", nativeQuery=true)
    Optional<Goals> findGoalsByGoal_id(Integer goal_id);

    @Query(value = "SELECT * FROM Goals" , nativeQuery = true)
    public List<Goals> getAllGoals();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Goals WHERE goal_id = :id", nativeQuery = true)
    public void deleteGoalsByGoal_id(Integer id);
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Goals (user_id, goal_type, target_value, current_value, start_date," +
                    "end_date,status) " +
                    "VALUES (?1, ?2, ?3, ?4, ?5,?6,?7)",
            nativeQuery = true
    )
    void addgoals(Integer user_id, String goal_type, String target_value, Float calories, Float protein
            ,Float carbohydrates,Float fats,String notes,String meal_time);

}

