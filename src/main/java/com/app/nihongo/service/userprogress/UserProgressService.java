package com.app.nihongo.service.userprogress;

import com.app.nihongo.dto.UserProgressDTO;
import org.springframework.http.ResponseEntity;

public interface UserProgressService {
    ResponseEntity<UserProgressDTO> saveUserProgress(UserProgressDTO userProgressDTO);
    ResponseEntity<UserProgressDTO> getHighestCompletedLessonByUserId(Integer userId);
    ResponseEntity<Integer> addXp(Integer userId, Integer lessonId, Integer score);
}
