package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Kanji_Lessons")
public class KanjiLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lessonId;
    private String name;
    private Integer displayOrder;
}
