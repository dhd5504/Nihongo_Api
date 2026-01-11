package com.app.nihongo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Fill_In_Blank_Questions")
public class FillInBlankQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fibId;

    @Column(columnDefinition = "TEXT")
    private String sentenceWithBlank;

    private String correctWord;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    // Getters and Setters
}
