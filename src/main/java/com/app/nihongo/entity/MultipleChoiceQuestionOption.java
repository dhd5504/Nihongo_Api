package com.app.nihongo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Multiple_Choice_Question_Options")
public class MultipleChoiceQuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer optionId;

    @ManyToOne
    @JoinColumn(name = "mcq_id")
    private MultipleChoiceQuestion multipleChoiceQuestion;

    @Column(columnDefinition = "TEXT")
    private String option;

    private Boolean isCorrect;
}
