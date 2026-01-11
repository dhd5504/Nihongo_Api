package com.app.nihongo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Sentence_Ordering_Questions")
public class SentenceOrderingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soqId;

    @Column(columnDefinition = "TEXT")
    private String vietnameseSentence;

    @Column(columnDefinition = "TEXT")
    private String japaneseWordsShuffled;

    @Column(columnDefinition = "TEXT")
    private String correctOrder;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    // Getters and Setters
}
