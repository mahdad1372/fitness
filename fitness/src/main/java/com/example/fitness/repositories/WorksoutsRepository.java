package com.example.fitness.repositories;
import com.example.fitness.entitties.Workouts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorksoutsRepository extends CrudRepository<Workouts, Integer> {
    @Query(value="SELECT * FROM Workouts a WHERE a.workout_id=?1", nativeQuery=true)
    Optional<Workouts> findWorkoutsByWorkout_id(Integer workout_id);

    @Query(value="SELECT * FROM workouts WHERE DATE(created_at) =?1 AND user_id=?2", nativeQuery=true)
    List<Workouts> findWorkoutsByday(String createdAt,Integer userId);

    @Query(value="SELECT * FROM workouts WHERE YEARWEEK(created_at, 1) = YEARWEEK(?1, 1) AND " +
            "user_id=?2", nativeQuery=true)
    List<Workouts> findWorkoutsByweek(String createdAt,Integer userId);

    @Query(value = "SELECT * FROM Workouts" , nativeQuery = true)
    public List<Workouts> getAllWorkouts();
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Workouts WHERE workout_id = :id", nativeQuery = true)
    public void deleteWorkoutsByWorkout_id(Integer id);
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Workouts (user_id, type, duration, calories_burned) " +
                    "VALUES (?1, ?2, ?3, ?4)",
            nativeQuery = true
    )
    void addworkouts(Integer user_id, String type, Integer duration, Float calories_burned);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Workouts SET rest_seconds = ?2,rpe = ?3,intensity_percent = ?4 WHERE workout_id = ?1 ",
            nativeQuery = true
    )
    void updateWorkoutsByWorkout_id(Integer workout_id, Integer rest_seconds, Integer rpe, Float intensity_percent);

}
