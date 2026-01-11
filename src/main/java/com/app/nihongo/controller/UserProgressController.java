package com.app.nihongo.controller;

import com.app.nihongo.dto.UserProgressDTO;
import com.app.nihongo.service.userprogress.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-progress")
public class UserProgressController {

    @Autowired
    private UserProgressService userProgressService;

    @PostMapping("/add")
    public ResponseEntity<UserProgressDTO> addUserProgress(@RequestBody UserProgressDTO userProgressDTO) {
        return userProgressService.saveUserProgress(userProgressDTO);
    }

    @GetMapping("/highest-lesson/{userId}")
    public ResponseEntity<UserProgressDTO> getHighestCompletedLesson(@PathVariable Integer userId) {
        return userProgressService.getHighestCompletedLessonByUserId(userId);
    }

    @PostMapping("/add-xp")
    public ResponseEntity<Integer> addXp(
            @RequestParam Integer userId,
            @RequestParam Integer lessonId,
            @RequestParam Integer score
    ) {
        return userProgressService.addXp(userId, lessonId, score);
    }
}
