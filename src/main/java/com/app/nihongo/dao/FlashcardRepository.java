package com.app.nihongo.dao;

import com.app.nihongo.entity.FlashcardQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlashcardRepository extends JpaRepository<FlashcardQuestion, Integer> {
    List<FlashcardQuestion> findByLesson_LessonId(Integer lessonId);
    Optional<FlashcardQuestion> findById(Integer id);
}
