package com.example.fitness.repositories;

import com.example.fitness.entitties.Daily_activities;
import com.example.fitness.entitties.Foods;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodsRepository extends CrudRepository<Foods, Integer> {
    @Query(value="SELECT * FROM Foods a WHERE a.food_id=?1", nativeQuery=true)
    Optional<Foods> findByFood_id(Integer activity_id);

    @Query(value = "SELECT * FROM Foods" , nativeQuery = true)
    public List<Foods> getAllFoods();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Foods WHERE food_id = :id", nativeQuery = true)
    public void deleteFoodsByFood_id(Integer id);
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Foods (user_id, food_name, category, calories, protein,carbohydrates,fats,notes) " +
                    "VALUES (?1, ?2, ?3, ?4, ?5,?6,?7,?8)",
            nativeQuery = true
    )
    void addDailyActivity(Integer user_id, String food_name, String category, Float calories, Float protein
            ,Float carbohydrates,Float fats,String notes);

}
