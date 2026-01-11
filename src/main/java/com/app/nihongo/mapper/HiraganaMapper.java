package com.app.nihongo.mapper;

import com.app.nihongo.dto.HiraganaDTO;
import com.app.nihongo.entity.Hiragana;
import org.springframework.stereotype.Component;

@Component
public class HiraganaMapper {
    public HiraganaDTO toDto(Hiragana hiragana) {
        HiraganaDTO dto = new HiraganaDTO();
        dto.setId(hiragana.getId());
        dto.setCharacter(hiragana.getCharacter());
        dto.setRomaji(hiragana.getRomaji());
        dto.setDisplayOrder(hiragana.getDisplayOrder());
        return dto;
    }

    public Hiragana toEntity(HiraganaDTO dto) {
        Hiragana hiragana = new Hiragana();
        hiragana.setId(dto.getId());
        hiragana.setCharacter(dto.getCharacter());
        hiragana.setRomaji(dto.getRomaji());
        hiragana.setDisplayOrder(dto.getDisplayOrder());
        return hiragana;
    }
}
