package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentenceMatchingQuestionDTO {
    private Integer id;
    private String question; // Japanese sentence
    private String correct; // Vietnamese sentence
    private Boolean completed;
    private List<MultipleChoiceQuestionOptionDTO> challengeOptions;
}
