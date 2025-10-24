package com.example.fitness.entitties;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.text.DecimalFormat;
import java.util.Date;


@Table(name = "daily_activities")
@Entity
public class Daily_activities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer activity_id;
    @Column(nullable = false)
    private Integer user_id;
    @UpdateTimestamp
    @Column(name = "date")
    private Date Date;
    @Column(nullable = false)
    private Integer steps;
    @Column(nullable = false)
    private Float sleep_hours;
    @Column(nullable = false)
    private DecimalFormat water_intake ;
    @Column(nullable = false)
    private DecimalFormat calories_burned ;
    @Column(nullable = false)
    private String mood ;
    @Column(nullable = false)
    private String notes ;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}