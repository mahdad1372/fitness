package com.example.fitness.entitties;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Table(name = "foods")
@Entity
public class Foods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer food_id;
    @Column(nullable = false)
    private Integer user_id;
    @Column(nullable = false)
    private String food_name ;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Float calories;
    @Column(nullable = false)
    private Float protein;
    @Column(nullable = false)
    private Float carbohydrates;
    @Column(nullable = false)
    private Float fats;
    @Column(nullable = false)
    private String notes;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
