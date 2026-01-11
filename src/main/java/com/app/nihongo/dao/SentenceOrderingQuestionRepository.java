package com.app.nihongo.dao;

import com.app.nihongo.entity.SentenceOrderingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceOrderingQuestionRepository extends JpaRepository<SentenceOrderingQuestion, Integer> {
    List<SentenceOrderingQuestion> findByLesson_LessonId(Integer lessonId);
}
