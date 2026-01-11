package com.app.nihongo.controller;

import com.app.nihongo.dto.UserFailedQuestionDTO;
import com.app.nihongo.enums.QuestionType;
import com.app.nihongo.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{lessonId}")
    public ResponseEntity<?> getQuestionsByTypeAndLessonId(
            @RequestParam Integer userId,
            @RequestParam("type") QuestionType type,
            @PathVariable Integer lessonId) {
        return questionService.getQuestionsByTypeAndLessonId(userId, type, lessonId);
    }
    @GetMapping("/practice")
    public ResponseEntity<List<UserFailedQuestionDTO>> getFailedQuestions(@RequestParam Integer userId) {
        List<UserFailedQuestionDTO> failedQuestions = questionService.getFailedQuestionsByUserId(userId);
        return ResponseEntity.ok(failedQuestions);
    }
    @GetMapping("/{type}/{questionId}")
    public ResponseEntity<?> getQuestionContent(
            @PathVariable QuestionType type,
            @PathVariable Integer questionId) {

        return questionService.getQuestionContentByTypeAndId(type, questionId);
    }

    @PostMapping("/right-answer")
    public ResponseEntity<String> rightAnswer(
            @RequestParam Integer userId,
            @RequestParam Integer questionId,
            @RequestParam("type") QuestionType type) {
        try {
            questionService.saveAnswer(userId, questionId, type, true);
            return ResponseEntity.ok("Right answer saved successfully.");
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/wrong-answer")
    public ResponseEntity<String> wrongAnswer(
            @RequestParam Integer userId,
            @RequestParam Integer questionId,
            @RequestParam("type") QuestionType type) {
        try {
            questionService.saveAnswer(userId, questionId, type, false);
            return ResponseEntity.ok("Wrong answer saved successfully.");
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/practice-by-unit")
    public ResponseEntity<?> getFailedQuestionsByUnit(
            @RequestParam Integer userId,
            @RequestParam Integer unitId,
            @RequestParam("type") QuestionType type) {
        try {
            return questionService.getFailedQuestionsByUnit(userId, unitId, type);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update-practice")
    public ResponseEntity<String> updatePractice(
            @RequestParam Integer userId,
            @RequestParam Integer unitId,
            @RequestParam("type") QuestionType type) {
        try {
            questionService.updatePractice(userId, unitId, type);
            return ResponseEntity.ok("All incorrect answers updated to correct.");
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
