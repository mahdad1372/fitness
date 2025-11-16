package com.example.fitness.entitties;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "health_metrics")
@Entity
public class Health_metrics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    @Column(nullable = false)
    private Integer user_id;
    @Column(nullable = false)
    private Float cholesterol;
    @Column(nullable = false)
    private Float blood_pressure;
    @Column(nullable = false)
    private Float heart_rate;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
}
