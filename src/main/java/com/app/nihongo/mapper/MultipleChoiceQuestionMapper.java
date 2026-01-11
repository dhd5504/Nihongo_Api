package com.app.nihongo.mapper;

import com.app.nihongo.dto.MultipleChoiceQuestionDTO;
import com.app.nihongo.dto.MultipleChoiceQuestionOptionDTO;
import com.app.nihongo.entity.MultipleChoiceQuestion;
import com.app.nihongo.entity.MultipleChoiceQuestionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MultipleChoiceQuestionMapper {

    @Autowired
    private MultipleChoiceQuestionOptionMapper optionMapper;

    public MultipleChoiceQuestionDTO toDto(MultipleChoiceQuestion entity, Boolean isCompleted) {
        MultipleChoiceQuestionDTO dto = new MultipleChoiceQuestionDTO();
        dto.setId(entity.getMcqId());
        dto.setQuestion(entity.getQuestionContent());
        dto.setCompleted(isCompleted);

        List<MultipleChoiceQuestionOptionDTO> options = entity.getOptions().stream()
                .map(optionMapper::toDto)
                .collect(Collectors.toList());
        dto.setChallengeOptions(options);

        return dto;
    }

    public MultipleChoiceQuestion toEntity(MultipleChoiceQuestionDTO dto) {
        MultipleChoiceQuestion entity = new MultipleChoiceQuestion();
        entity.setMcqId(dto.getId());
        entity.setQuestionContent(dto.getQuestion());

        List<MultipleChoiceQuestionOption> options = dto.getChallengeOptions().stream()
                .map(optionMapper::toEntity)
                .collect(Collectors.toList());
        entity.setOptions(options);

        return entity;
    }
}
