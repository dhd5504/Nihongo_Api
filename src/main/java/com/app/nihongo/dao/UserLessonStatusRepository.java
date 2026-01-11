package com.app.nihongo.dao;

import com.app.nihongo.entity.UserLessonStatus;
import com.app.nihongo.entity.UserLessonStatusKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLessonStatusRepository extends JpaRepository<UserLessonStatus, UserLessonStatusKey> {
    Optional<UserLessonStatus> findById_UserIdAndId_LessonId(Integer userId, Integer lessonId);
    List<UserLessonStatus> findById_UserId(Integer userId);
    boolean existsById_UserId(Integer userId);
}

