package com.app.nihongo.service.unit;

import com.app.nihongo.dao.LessonRepository;
import com.app.nihongo.dao.UnitRepository;
import com.app.nihongo.dao.UserLessonStatusRepository;
import com.app.nihongo.dao.UserRepository;
import com.app.nihongo.dto.LessonDTO;
import com.app.nihongo.dto.UnitDTO;
import com.app.nihongo.entity.*;
import com.app.nihongo.mapper.LessonMapper;
import com.app.nihongo.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private UserLessonStatusRepository userLessonStatusRepository;

    @Override
    public ResponseEntity<List<UnitDTO>> getAllUnits() {
            List<Unit> units = unitRepository.findAll();
            List<UnitDTO> result = new ArrayList<>();

            for (Unit unit : units) {
                List<Lesson> lessons = lessonRepository.findByUnit_UnitId(unit.getUnitId());
                List<LessonDTO> lessonDTOs = lessons.stream()
                        .map(lessonMapper::toDto)
                        .collect(Collectors.toList());

                UnitDTO unitWithLessonsDTO =  unitMapper.toDto(unit);
                unitWithLessonsDTO.setLessons(lessonDTOs);
                result.add(unitWithLessonsDTO);
            }
            return ResponseEntity.ok(result);

    }

    @Override
    public ResponseEntity<List<UnitDTO>> getUnitsByLevel(String level) {
        List<Unit> units = unitRepository.findByLevel(level);
        List<UnitDTO> result = new ArrayList<>();

        for (Unit unit : units) {
            List<Lesson> lessons = lessonRepository.findByUnit_UnitId(unit.getUnitId());
            List<LessonDTO> lessonDTOs = lessons.stream()
                    .map(lessonMapper::toDto)
                    .collect(Collectors.toList());

            UnitDTO unitWithLessonsDTO =  unitMapper.toDto(unit);
            unitWithLessonsDTO.setLessons(lessonDTOs);
            result.add(unitWithLessonsDTO);
        }
        return ResponseEntity.ok(result);

    }
    @Override
    public ResponseEntity<UnitDTO> getUnitById(Integer unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new RuntimeException("Unit not found"));
        return ResponseEntity.ok(unitMapper.toDto(unit));
    }

    @Override
    public ResponseEntity<List<UnitDTO>> getAllUnitsWithStatus(Integer userId) {

        List<UserLessonStatus> userStatuses = userLessonStatusRepository.findById_UserId(userId);

        if (userStatuses.isEmpty()) {
            Lesson firstLesson = lessonRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Lesson 1 not found"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserLessonStatusKey key = new UserLessonStatusKey(userId, firstLesson.getLessonId());
            UserLessonStatus initialStatus = new UserLessonStatus();
            initialStatus.setId(key);
            initialStatus.setUser(user);
            initialStatus.setLesson(firstLesson);
            initialStatus.setStatus("current");

            userLessonStatusRepository.save(initialStatus);

            userStatuses.add(initialStatus);
        }

        List<Unit> units = unitRepository.findAll();
        List<UnitDTO> result = new ArrayList<>();

        for (Unit unit : units) {
            List<Lesson> lessons = lessonRepository.findByUnit_UnitId(unit.getUnitId());
            List<LessonDTO> lessonDTOs = lessons.stream().map(lesson -> {
                String status = userStatuses.stream()
                        .filter(us -> us.getLesson().getLessonId().equals(lesson.getLessonId()))
                        .map(UserLessonStatus::getStatus)
                        .findFirst()
                        .orElse("locked");
                return lessonMapper.toDto(lesson, status);
            }).collect(Collectors.toList());

            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setId(unit.getUnitId());
            unitDTO.setDisplayOrder(unit.getDisplayOrder());
            unitDTO.setTitle(unit.getName());
            unitDTO.setDescription(unit.getDescription());
            unitDTO.setLevel(unit.getLevel());
            unitDTO.setLessons(lessonDTOs);

            result.add(unitDTO);
        }

        return ResponseEntity.ok(result);
    }

    @Override
    public boolean userLessonStatusExist(Integer userId) {
        return userLessonStatusRepository.existsById_UserId(userId);
    }

}
