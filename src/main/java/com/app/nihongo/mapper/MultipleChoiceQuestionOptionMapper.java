package com.app.nihongo.mapper;

import com.app.nihongo.dto.MultipleChoiceQuestionOptionDTO;
import com.app.nihongo.entity.MultipleChoiceQuestionOption;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceQuestionOptionMapper {

    public MultipleChoiceQuestionOptionDTO toDto(MultipleChoiceQuestionOption entity) {
        MultipleChoiceQuestionOptionDTO dto = new MultipleChoiceQuestionOptionDTO();
        dto.setId(entity.getOptionId());
        dto.setOption(entity.getOption());
        dto.setIsCorrect(entity.getIsCorrect());
        return dto;
    }

    public MultipleChoiceQuestionOption toEntity(MultipleChoiceQuestionOptionDTO dto) {
        MultipleChoiceQuestionOption entity = new MultipleChoiceQuestionOption();
        entity.setOptionId(dto.getId());
        entity.setOption(dto.getOption());
        entity.setIsCorrect(dto.getIsCorrect());
        return entity;
    }
}
