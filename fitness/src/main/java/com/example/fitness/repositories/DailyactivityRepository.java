package com.example.fitness.repositories;
import com.example.fitness.entitties.Daily_activities;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyactivityRepository extends CrudRepository<Daily_activities, Integer> {
    @Query(value="SELECT * FROM daily_activities a WHERE a.activity_id=?1", nativeQuery=true)
    Optional<Daily_activities> findByActivity_id(Integer activity_id);

    @Query(value = "SELECT * FROM daily_activities" , nativeQuery = true)
    public List<Daily_activities> getaAllDailyActivities();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM daily_activities WHERE activity_id = :id", nativeQuery = true)
    public void deleteDaily_activitiesById(Integer id);
//    @Query(value="SELECT * FROM Users a WHERE a.firstname=?1 AND a.email=?2", nativeQuery=true)
//    List<User> fetchallfromcustomerswithName(String first_name,String email);
//    @Modifying
    @Transactional
    @Query(
            value =
                    "INSERT INTO daily_activities (user_id,Date,steps,sleep_hours,water_intake,calories_burned,mood,notes) " +
                            "values (?1,?2,?3,?4,?5,?6,?7,?8)",
            nativeQuery = true)
    void addDailyActivity(Integer user_id, Date Date, Integer steps, Float sleep_hours, DecimalFormat water_intake
            , DecimalFormat calories_burned, String mood, String notes);

}
