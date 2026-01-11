package com.app.nihongo.service.userprogress;

import com.app.nihongo.dao.LessonRepository;
import com.app.nihongo.dao.UserProgressRepository;
import com.app.nihongo.dao.UserRepository;
import com.app.nihongo.dto.UserProgressDTO;
import com.app.nihongo.entity.Lesson;
import com.app.nihongo.entity.User;
import com.app.nihongo.entity.UserProgress;
import com.app.nihongo.mapper.UserProgressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserProgressServiceImpl implements UserProgressService {

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserProgressMapper userProgressMapper;

    @Override
    public ResponseEntity<UserProgressDTO> saveUserProgress(UserProgressDTO userProgressDTO) {
        User user = userRepository.findById(userProgressDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Lesson lesson = lessonRepository.findById(userProgressDTO.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        UserProgress userProgress = userProgressMapper.toEntity(userProgressDTO, user, lesson);
        UserProgress savedProgress = userProgressRepository.save(userProgress);
        UserProgressDTO responseDTO = userProgressMapper.toDto(savedProgress);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<UserProgressDTO> getHighestCompletedLessonByUserId(Integer userId) {
        UserProgress highestProgress = userProgressRepository.findTopByUser_UserIdOrderByLesson_LessonIdDesc(userId)
                .orElse(null);
        if (highestProgress != null) {
            UserProgressDTO highestProgressDTO = userProgressMapper.toDto(highestProgress);
            return ResponseEntity.ok(highestProgressDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Integer> addXp(Integer userId, Integer lessonId, Integer score) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        UserProgress userProgress = new UserProgress();
        userProgress.setUser(user);
        userProgress.setLesson(lesson);
        userProgress.setScore(score);
        userProgressRepository.save(userProgress);

        Integer totalScore = userProgressRepository.sumScoreByUserId(userId);
        return ResponseEntity.ok(totalScore);
    }
}
