package com.app.nihongo.controller;

import com.app.nihongo.dto.LessonDTO;
import com.app.nihongo.entity.UserLessonStatus;
import com.app.nihongo.service.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByUnitIdWithStatus(
            @PathVariable Integer unitId,
            @RequestParam Integer userId) {
        return lessonService.getLessonsByUnitIdWithStatus(unitId, userId);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateLessonStatus(
            @RequestParam Integer userId,
            @RequestParam Integer lessonId) {
        return lessonService.updateLessonStatus(userId, lessonId, "completed");
    }
    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Integer lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @PostMapping("/set-level")
    public ResponseEntity<?> setLevelUser(
            @RequestParam Integer userId,
            @RequestParam String level) {
        return lessonService.setLevelUser(userId, level);
    }
}
