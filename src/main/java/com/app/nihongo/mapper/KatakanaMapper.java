package com.app.nihongo.mapper;

import com.app.nihongo.dto.KatakanaDTO;
import com.app.nihongo.entity.Katakana;
import org.springframework.stereotype.Component;

@Component
public class KatakanaMapper {
    public KatakanaDTO toDto(Katakana katakana) {
        KatakanaDTO dto = new KatakanaDTO();
        dto.setId(katakana.getId());
        dto.setCharacter(katakana.getCharacter());
        dto.setRomaji(katakana.getRomaji());
        dto.setDisplayOrder(katakana.getDisplayOrder());
        return dto;
    }

    public Katakana toEntity(KatakanaDTO dto) {
        Katakana katakana = new Katakana();
        katakana.setId(dto.getId());
        katakana.setCharacter(dto.getCharacter());
        katakana.setRomaji(dto.getRomaji());
        katakana.setDisplayOrder(dto.getDisplayOrder());
        return katakana;
    }
}
