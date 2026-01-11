package com.app.nihongo.controller;

import com.app.nihongo.dto.KanjiDTO;
import com.app.nihongo.service.kanji.KanjiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/characters/kanji")
public class KanjiController {

    @Autowired
    private KanjiService kanjiService;

    @GetMapping("/lesson/{kanjiLessonId}")
    public ResponseEntity<List<KanjiDTO>> getKanjiByLessonId(@PathVariable Integer kanjiLessonId) {
        List<KanjiDTO> kanjiDTOs = kanjiService.getKanjiByLessonId(kanjiLessonId);
        return ResponseEntity.ok(kanjiDTOs);
    }
}
