package com.app.nihongo.mapper;

import com.app.nihongo.dto.FlashcardDTO;
import com.app.nihongo.entity.FlashcardQuestion;
import org.springframework.stereotype.Component;

@Component
public class FlashcardMapper {

    public FlashcardDTO toDto(FlashcardQuestion entity) {
        FlashcardDTO dto = new FlashcardDTO();
        dto.setId(entity.getFlashcardId());
        dto.setWord(entity.getJapaneseWord());
        dto.setMeaning(entity.getMeaning());
        return dto;
    }

    public FlashcardQuestion toEntity(FlashcardDTO dto) {
        FlashcardQuestion entity = new FlashcardQuestion();
        entity.setFlashcardId(dto.getId());
        entity.setJapaneseWord(dto.getWord());
        entity.setMeaning(dto.getMeaning());
        return entity;
    }
}
