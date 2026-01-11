package com.app.nihongo.dao;

import com.app.nihongo.entity.SentenceMatchingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentenceMatchingQuestionRepository extends JpaRepository<SentenceMatchingQuestion, Integer> {
    List<SentenceMatchingQuestion> findByLesson_LessonId(Integer lessonId);
}
