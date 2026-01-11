package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User_Multiple_Choice_Questions")
public class UserMultipleChoiceQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "mcq_id", nullable = false)
    private MultipleChoiceQuestion multipleChoiceQuestion;

    @Column(nullable = false)
    private Boolean isCompleted = false;

    @Column
    private Integer score;
}
