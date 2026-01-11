package com.app.nihongo.service.level;

import com.app.nihongo.dto.LevelDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LevelService {
    ResponseEntity<List<LevelDTO>> getAllLevels();
}
