package com.app.nihongo.mapper;

import com.app.nihongo.dto.MultipleChoiceQuestionOptionDTO;
import com.app.nihongo.dto.SentenceMatchingQuestionDTO;
import com.app.nihongo.entity.SentenceMatchingQuestion;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SentenceMatchingQuestionMapper {

    public SentenceMatchingQuestionDTO toDto(SentenceMatchingQuestion entity, Boolean isCompleted,
            List<SentenceMatchingQuestion> allQuestions) {
        SentenceMatchingQuestionDTO dto = new SentenceMatchingQuestionDTO();
        dto.setId(entity.getSmqId());
        dto.setQuestion(entity.getJapaneseSentence());
        dto.setCorrect(entity.getVietnameseSentence());
        dto.setCompleted(isCompleted);

        // Generate challenge options
        List<MultipleChoiceQuestionOptionDTO> options = new ArrayList<>();
        // Correct option
        options.add(new MultipleChoiceQuestionOptionDTO(0, entity.getVietnameseSentence(), true));

        // Distractors (up to 3)
        List<String> distractors = allQuestions.stream()
                .filter(q -> !q.getSmqId().equals(entity.getSmqId()))
                .map(SentenceMatchingQuestion::getVietnameseSentence)
                .collect(Collectors.toList());
        Collections.shuffle(distractors);

        for (int i = 0; i < Math.min(3, distractors.size()); i++) {
            options.add(new MultipleChoiceQuestionOptionDTO(i + 1, distractors.get(i), false));
        }
        Collections.shuffle(options);
        dto.setChallengeOptions(options);

        return dto;
    }

    public SentenceMatchingQuestion toEntity(SentenceMatchingQuestionDTO dto) {
        SentenceMatchingQuestion entity = new SentenceMatchingQuestion();
        entity.setSmqId(dto.getId());
        entity.setJapaneseSentence(dto.getQuestion());
        entity.setVietnameseSentence(dto.getCorrect());
        return entity;
    }
}
