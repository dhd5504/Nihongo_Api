package com.app.nihongo.dao;

import com.app.nihongo.entity.MultipleChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MultipleChoiceQuestionRepository extends JpaRepository<MultipleChoiceQuestion, Integer> {
    List<MultipleChoiceQuestion> findByLesson_LessonId(Integer lessonId);
    Optional<MultipleChoiceQuestion> findById(Integer id);
}
