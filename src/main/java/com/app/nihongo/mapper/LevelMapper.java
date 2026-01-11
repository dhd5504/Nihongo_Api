package com.app.nihongo.mapper;

import com.app.nihongo.dto.LevelDTO;
import com.app.nihongo.entity.Level;
import org.springframework.stereotype.Component;

@Component
public class LevelMapper {
    public LevelDTO toDto(Level level) {
        LevelDTO dto = new LevelDTO();
        dto.setId(level.getLevelId());
        dto.setLevelName(level.getLevelName());
        dto.setDisplayOrder(level.getDisplayOrder());
        return dto;
    }
}
