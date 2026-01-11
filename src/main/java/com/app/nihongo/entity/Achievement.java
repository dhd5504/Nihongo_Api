package com.app.nihongo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer achievementId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String achievementTitle;
    private LocalDateTime achievementDate;

    // Getters and Setters
}

