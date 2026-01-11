package com.app.nihongo.dao;

import com.app.nihongo.entity.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KanjiRepository extends JpaRepository<Kanji, Integer> {
    List<Kanji> findByKanjiLesson_LessonId(Integer kanjiLessonId);
}
