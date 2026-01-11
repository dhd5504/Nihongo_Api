package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionDTO {
    private Integer id;
    private String question;
    private Boolean completed;
    private List<MultipleChoiceQuestionOptionDTO> challengeOptions;

    public MultipleChoiceQuestionDTO(Integer id, String question, Boolean completed){
        this.id = id;
        this.question = question;
        this.completed = completed;
    }
}
