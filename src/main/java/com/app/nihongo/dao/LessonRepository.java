package com.app.nihongo.dao;

import com.app.nihongo.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByUnit_UnitId(Integer unitId);
    @Query("SELECT a.lessonId FROM Lesson a " +
            "LEFT JOIN Unit b ON a.unit.unitId = b.unitId " +
            "WHERE a.unit.unitId < (SELECT u.displayOrder FROM Unit u WHERE u.level = :level ORDER BY u.displayOrder ASC LIMIT 1)")
    List<Integer> findAllLessonLessThanLevel( @Param("level") String level);
}
