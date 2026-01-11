package com.app.nihongo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Lesson_Questions")
public class LessonQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lessonQuestionId;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private Integer questionId;
    private String questionType;

    // Getters and Setters
}

