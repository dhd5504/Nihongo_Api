package com.app.nihongo.dao;


import com.app.nihongo.dto.UserExpDTO;
import com.app.nihongo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "user")
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUserId(int userId);
    public User findByEmail(String email);
    public User findByUsername(String username);

    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    @Query("SELECT COUNT(e.id) * 10 AS exp " +
            "FROM User a " +
            "LEFT JOIN UserLessonStatus b ON a.userId = b.id.userId " +
            "LEFT JOIN Lesson c ON b.id.lessonId = c.lessonId " +
            "LEFT JOIN Unit f ON c.unit.unitId = f.unitId " +
            "LEFT JOIN MultipleChoiceQuestion d ON c.lessonId = d.lesson.lessonId " +
            "LEFT JOIN UserMultipleChoiceQuestion e ON d.mcqId = e.multipleChoiceQuestion.mcqId AND a.userId = e.user.userId " +
            "WHERE a.userId = :userId AND b.status = 'completed' AND e.isCompleted = true")
    Integer calculateExperience(@Param("userId") Integer userId);

    @Query("""
    SELECT new com.app.nihongo.dto.UserExpDTO(
        a.userId,
        a.username,
        CAST(COALESCE(SUM(CASE WHEN e.isCompleted = TRUE THEN 10 ELSE 0 END), 0) AS integer)
    )
    FROM User a
    LEFT JOIN UserLessonStatus b ON a.userId = b.id.userId
    LEFT JOIN Lesson c ON b.id.lessonId = c.lessonId
    LEFT JOIN Unit f ON c.unit.unitId = f.unitId
    LEFT JOIN MultipleChoiceQuestion d ON c.lessonId = d.lesson.lessonId
    LEFT JOIN UserMultipleChoiceQuestion e ON d.mcqId = e.multipleChoiceQuestion.mcqId AND a.userId = e.user.userId
    WHERE f.level = :level AND b.status = 'completed'
    GROUP BY a.userId
""")
    List<UserExpDTO> findUserExpByLevel(@Param("level") String level);


}
