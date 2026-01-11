package com.app.nihongo.controller;

import com.app.nihongo.dto.HiraganaDTO;
import com.app.nihongo.service.hiragana.HiraganaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/characters/hiragana")
public class HiraganaController {
    @Autowired
    private HiraganaService hiraganaService;


    @GetMapping
    public ResponseEntity<List<HiraganaDTO>> getAllHiraganaCharacters() {
        return ResponseEntity.ok(hiraganaService.getAllHiraganaCharacters());
    }
}
