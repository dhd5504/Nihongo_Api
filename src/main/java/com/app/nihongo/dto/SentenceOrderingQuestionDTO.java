package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentenceOrderingQuestionDTO {
    private Integer id;
    private String question; // Vietnamese sentence
    private String correct; // Correct Japanese sentence
    private List<String> words; // Shuffled words
    private String type = "SENTENCE_ORDERING";
    private boolean completed;
}
