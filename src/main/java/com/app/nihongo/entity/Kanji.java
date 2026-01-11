package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Kanji")
public class Kanji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kanjiId;

    private String kanjiCharacter;
    private Integer displayOrder;
    @Column(columnDefinition = "TEXT")
    private String meaning;


    private String pronunciation;

    @ManyToOne
    @JoinColumn(name = "kanji_lesson_id")
    private KanjiLesson kanjiLesson;

    // Getters and Setters
}
