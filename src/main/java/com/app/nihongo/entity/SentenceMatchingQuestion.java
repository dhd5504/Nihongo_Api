package com.app.nihongo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Sentence_Matching_Questions")
public class SentenceMatchingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer smqId;

    @Column(columnDefinition = "TEXT")
    private String vietnameseSentence;

    @Column(columnDefinition = "TEXT")
    private String japaneseSentence;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    // Getters and Setters
}

