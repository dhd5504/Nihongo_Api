package com.app.nihongo.mapper;

import com.app.nihongo.dto.UserProgressDTO;
import com.app.nihongo.entity.UserProgress;
import com.app.nihongo.entity.User;
import com.app.nihongo.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class UserProgressMapper {

    public UserProgressDTO toDto(UserProgress userProgress) {
        UserProgressDTO dto = new UserProgressDTO();
        dto.setProgressId(userProgress.getProgressId());
        dto.setUserId(userProgress.getUser().getUserId());
        dto.setLessonId(userProgress.getLesson().getLessonId());
        dto.setScore(userProgress.getScore());
        return dto;
    }

    public UserProgress toEntity(UserProgressDTO dto, User user, Lesson lesson) {
        UserProgress userProgress = new UserProgress();
        userProgress.setUser(user);
        userProgress.setLesson(lesson);
        userProgress.setScore(dto.getScore());
        return userProgress;
    }
}
