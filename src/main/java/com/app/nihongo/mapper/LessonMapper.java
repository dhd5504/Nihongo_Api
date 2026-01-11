package com.app.nihongo.mapper;

import com.app.nihongo.dto.LessonDTO;
import com.app.nihongo.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public LessonDTO toDto(Lesson lesson, String status) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(lesson.getLessonId());
        lessonDTO.setOrder(lesson.getDisplayOrder());
        lessonDTO.setName(lesson.getLessonTitle());
        lessonDTO.setType(lesson.getType());
        lessonDTO.setStatus(status);
        return lessonDTO;
    }
    public LessonDTO toDto(Lesson lesson) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(lesson.getLessonId());
        lessonDTO.setOrder(lesson.getDisplayOrder());
        lessonDTO.setName(lesson.getLessonTitle());
        lessonDTO.setType(lesson.getType());
        return lessonDTO;
    }
}
