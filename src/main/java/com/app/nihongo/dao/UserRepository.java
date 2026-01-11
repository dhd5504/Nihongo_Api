package com.app.nihongo.dao;

import com.app.nihongo.dto.UserExpDTO;
import com.app.nihongo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "user")
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUserId(int userId);

    public User findByEmail(String email);

    public User findByUsername(String username);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    @Query("""
                SELECT new com.app.nihongo.dto.UserExpDTO(
                    u.userId,
                    u.username,
                    CAST(SUM(up.score) AS integer)
                )
                FROM User u
                JOIN UserProgress up ON u.userId = up.user.userId
                JOIN Lesson l ON up.lesson.lessonId = l.lessonId
                JOIN Unit un ON l.unit.unitId = un.unitId
                WHERE un.level = :level
                GROUP BY u.userId, u.username
                ORDER BY SUM(up.score) DESC
            """)
    List<UserExpDTO> findUserExpByLevel(@Param("level") String level);

}
