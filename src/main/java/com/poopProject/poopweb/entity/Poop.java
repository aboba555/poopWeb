package com.poopProject.poopweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "poops")
@NoArgsConstructor
@AllArgsConstructor
public class Poop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String form;

    private String color;

    private String weight;

    private String painLevel = "1";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;


    public Poop(String form, String color, String weight,String painLevel) {
        this.form = form;
        this.color = color;
        this.weight = weight;
        this.painLevel = painLevel;
    }
}
