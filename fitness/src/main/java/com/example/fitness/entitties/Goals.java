package com.example.fitness.entitties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Table(name = "goals")
@Entity
public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer goal_id;
    @Column(nullable = false)
    private Integer user_id;
    @Column(nullable = false)
    private String goal_type ;
    @Column(nullable = false)
    private String target_value;
    @Column(nullable = false)
    private Float current_value;
    @Column(nullable = false)
    private Float start_date;
    @Column(nullable = false)
    private Float end_date;
    @Column(nullable = false)
    private Float status;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
