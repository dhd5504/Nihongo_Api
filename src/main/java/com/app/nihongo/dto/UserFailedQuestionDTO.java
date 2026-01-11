package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFailedQuestionDTO {
    private Integer questionId;
    private String questionType;
}
