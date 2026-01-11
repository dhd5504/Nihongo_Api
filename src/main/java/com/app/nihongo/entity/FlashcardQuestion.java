package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Flashcard_Questions")
public class FlashcardQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flashcardId;

    private String japaneseWord;
    private String meaning;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    // Getters and Setters
}
