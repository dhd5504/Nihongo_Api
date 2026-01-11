package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Multiple_Choice_Questions")
public class MultipleChoiceQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mcqId;

    @Column(columnDefinition = "TEXT")
    private String questionContent;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @OneToMany(mappedBy = "multipleChoiceQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MultipleChoiceQuestionOption> options;

}
