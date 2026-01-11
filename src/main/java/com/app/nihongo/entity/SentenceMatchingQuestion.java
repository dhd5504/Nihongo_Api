package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
