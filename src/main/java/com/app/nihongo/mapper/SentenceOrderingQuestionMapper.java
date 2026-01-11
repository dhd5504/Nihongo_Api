package com.app.nihongo.mapper;

import com.app.nihongo.dto.SentenceOrderingQuestionDTO;
import com.app.nihongo.entity.SentenceOrderingQuestion;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SentenceOrderingQuestionMapper {

    public SentenceOrderingQuestionDTO toDto(SentenceOrderingQuestion entity, boolean completed) {
        SentenceOrderingQuestionDTO dto = new SentenceOrderingQuestionDTO();
        dto.setId(entity.getSoqId());
        dto.setQuestion(entity.getVietnameseSentence());

        // Làm sạch correct_order: loại bỏ dấu | để Frontend so khớp chuỗi assembled
        // liền mạch
        if (entity.getCorrectOrder() != null) {
            dto.setCorrect(entity.getCorrectOrder().replace("|", ""));
        }

        dto.setCompleted(completed);

        // Phân tách từ bằng dấu | theo format thực tế của bạn
        if (entity.getJapaneseWordsShuffled() != null) {
            List<String> words = Arrays.stream(entity.getJapaneseWordsShuffled().split("\\|"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            dto.setWords(words);
        }

        return dto;
    }
}
