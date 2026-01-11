package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailedMultipleChoiceQuestionDTO {
    private Integer mcqId;
    private String questionContent;
    private Boolean isCompleted;
    private Integer optionId;
    private String option;
    private Boolean isCorrect;


}
