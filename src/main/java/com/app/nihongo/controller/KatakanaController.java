package com.app.nihongo.controller;

import com.app.nihongo.dto.KatakanaDTO;
import com.app.nihongo.service.katakana.KatakanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/characters/katakana")
public class KatakanaController {
    @Autowired
    private KatakanaService katakanaService;

    @GetMapping
    public ResponseEntity<List<KatakanaDTO>> getAllKatakanaCharacters() {
        return ResponseEntity.ok(katakanaService.getAllKatakanaCharacters());
    }
}
