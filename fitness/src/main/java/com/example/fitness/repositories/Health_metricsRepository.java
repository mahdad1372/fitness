package com.example.fitness.repositories;
import com.example.fitness.entitties.Health_metrics;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Health_metricsRepository extends CrudRepository<Health_metrics, Integer> {
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Health_metrics (user_id, cholesterol, blood_pressure, heart_rate) " +
                    "VALUES (?1, ?2, ?3, ?4)",
            nativeQuery = true
    )
    void addHealth_metrics(Integer user_id, Float cholesterol, Float blood_pressure, Float heart_rate);

}
