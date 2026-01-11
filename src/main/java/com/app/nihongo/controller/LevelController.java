package com.app.nihongo.controller;

import com.app.nihongo.dto.LevelDTO;
import com.app.nihongo.service.level.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping
    public ResponseEntity<List<LevelDTO>> getAllLevels() {
        return levelService.getAllLevels();
    }
}
