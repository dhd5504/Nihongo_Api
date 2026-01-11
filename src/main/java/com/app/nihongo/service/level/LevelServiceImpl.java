package com.app.nihongo.service.level;

import com.app.nihongo.dao.LevelRepository;
import com.app.nihongo.dto.LevelDTO;
import com.app.nihongo.entity.Level;
import com.app.nihongo.mapper.LevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private LevelMapper levelMapper;

    @Override
    public ResponseEntity<List<LevelDTO>> getAllLevels() {
        List<Level> levels = levelRepository.findAll();
        List<LevelDTO> levelDTOs = levels.stream()
                .map(levelMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(levelDTOs);
    }
}
