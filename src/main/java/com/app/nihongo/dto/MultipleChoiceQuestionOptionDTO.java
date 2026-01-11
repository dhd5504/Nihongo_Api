package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionOptionDTO {
    private Integer id;
    private String option;
    private Boolean isCorrect;
}