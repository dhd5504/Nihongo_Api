package com.app.nihongo.service.lesson;

import com.app.nihongo.dto.LessonDTO;
import com.app.nihongo.entity.UserLessonStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LessonService {
//    ResponseEntity<List<LessonDTO>> getLessonsByUnitId(Integer unitId);
    ResponseEntity<LessonDTO> getLessonById(Integer lessonId);
    ResponseEntity<List<LessonDTO>> getLessonsByUnitIdWithStatus(Integer unitId, Integer userId);

    ResponseEntity<?> updateLessonStatus(Integer userId, Integer lessonId, String status);

    ResponseEntity<?> setLevelUser(Integer userId, String level);
}
