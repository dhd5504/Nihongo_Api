package com.app.nihongo.service.question;

import com.app.nihongo.dao.FlashcardRepository;
import com.app.nihongo.dao.MultipleChoiceQuestionRepository;
import com.app.nihongo.dao.UserMultipleChoiceQuestionRepository;
import com.app.nihongo.dto.*;
import com.app.nihongo.entity.MultipleChoiceQuestion;
import com.app.nihongo.entity.User;
import com.app.nihongo.entity.UserMultipleChoiceQuestion;
import com.app.nihongo.enums.QuestionType;
import com.app.nihongo.mapper.FlashcardMapper;
import com.app.nihongo.mapper.MultipleChoiceQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    @Autowired
    private UserMultipleChoiceQuestionRepository userMultipleChoiceQuestionRepository;

    @Autowired
    private FlashcardMapper flashcardMapper;

    @Autowired
    private MultipleChoiceQuestionMapper multipleChoiceQuestionMapper;

    @Override
    public ResponseEntity<?> getQuestionsByTypeAndLessonId(Integer userId, QuestionType type, Integer lessonId) {
        switch (type) {
            case FLASHCARD:
                List<FlashcardDTO> flashcards = flashcardRepository.findByLesson_LessonId(lessonId)
                        .stream()
                        .map(flashcardMapper::toDto)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(flashcards);

            case MULTIPLE_CHOICE:
                List<MultipleChoiceQuestionDTO> mcqs = multipleChoiceQuestionRepository.findByLesson_LessonId(lessonId)
                        .stream()
                        .map(mcq -> {
                            Boolean isCompleted = userMultipleChoiceQuestionRepository.existsByUser_UserIdAndMultipleChoiceQuestion_McqId(userId, mcq.getMcqId());
                            return multipleChoiceQuestionMapper.toDto(mcq, isCompleted);
                        })
                        .collect(Collectors.toList());
                return ResponseEntity.ok(mcqs);

            default:
                throw new IllegalArgumentException("Invalid question type");
        }
    }

    @Override
    public List<UserFailedQuestionDTO> getFailedQuestionsByUserId(Integer userId) {

        List<UserFailedQuestionDTO> failedQuestions = userMultipleChoiceQuestionRepository.findFailedMultipleChoiceQuestionsByUserId(userId);

        return failedQuestions;
    }

    @Override
    public ResponseEntity<?> getQuestionContentByTypeAndId(QuestionType type, Integer questionId) {
        switch (type) {
            case FLASHCARD:
                FlashcardDTO flashcard = flashcardRepository.findById(questionId)
                        .map(flashcardMapper::toDto)
                        .orElseThrow(() -> new IllegalArgumentException("Flashcard not found with ID: " + questionId));
                return ResponseEntity.ok(flashcard);

            case MULTIPLE_CHOICE:
                MultipleChoiceQuestionDTO mcq = multipleChoiceQuestionRepository.findById(questionId)
                        .map(mcqEntity -> {
                            Boolean isCompleted = userMultipleChoiceQuestionRepository.existsByUser_UserIdAndMultipleChoiceQuestion_McqId(null, mcqEntity.getMcqId());
                            return multipleChoiceQuestionMapper.toDto(mcqEntity, isCompleted);
                        })
                        .orElseThrow(() -> new IllegalArgumentException("Multiple choice question not found with ID: " + questionId));
                return ResponseEntity.ok(mcq);

            default:
                throw new IllegalArgumentException("Invalid question type: " + type);
        }
    }

    @Override
    public void saveAnswer(Integer userId, Integer questionId, QuestionType type, boolean isCorrect) {
        // hiện tại chỉ có MULTIPLE_CHOICE
        switch (type) {
            case MULTIPLE_CHOICE:
        Optional<UserMultipleChoiceQuestion> existingAnswer = userMultipleChoiceQuestionRepository
                .findByUser_UserIdAndMultipleChoiceQuestion_McqId(userId, questionId);

        if (existingAnswer.isPresent()) {

            UserMultipleChoiceQuestion userAnswer = existingAnswer.get();
            userAnswer.setIsCompleted(isCorrect);
            userMultipleChoiceQuestionRepository.save(userAnswer);
        } else {

            UserMultipleChoiceQuestion newAnswer = new UserMultipleChoiceQuestion();
            User user = new User();
            user.setUserId(userId);
            newAnswer.setUser(user);
            MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
            multipleChoiceQuestion.setMcqId(questionId);
            newAnswer.setMultipleChoiceQuestion(multipleChoiceQuestion);
            newAnswer.setIsCompleted(isCorrect);
            userMultipleChoiceQuestionRepository.save(newAnswer);
        }
        break;
            default:
                throw new IllegalArgumentException("Invalid question type: " + type);
        }
    }
    @Override
    public ResponseEntity<?> getFailedQuestionsByUnit(Integer userId, Integer unitId, QuestionType type) {
        switch (type) {
            case MULTIPLE_CHOICE:

        // dữ liệu phẳng
        List<FailedMultipleChoiceQuestionDTO> flatQuestions = userMultipleChoiceQuestionRepository
                .findFailedMultipleChoiceQuestionsByUserIdAndUnitId(userId, unitId);

        //nhóm theo mcq_id
        Map<Integer, MultipleChoiceQuestionDTO> questionMap = new HashMap<>();

        for (FailedMultipleChoiceQuestionDTO dto : flatQuestions) {
            // câu hỏi chưa có trong map -> tạo mới
            if (!questionMap.containsKey(dto.getMcqId())) {
                questionMap.put(dto.getMcqId(), new MultipleChoiceQuestionDTO(dto.getMcqId(), dto.getQuestionContent(), dto.getIsCompleted()));
                questionMap.get(dto.getMcqId()).setChallengeOptions(new ArrayList<>());
            }
            //add option
            MultipleChoiceQuestionOptionDTO optionDTO = new MultipleChoiceQuestionOptionDTO(dto.getOptionId(), dto.getOption(), dto.getIsCorrect());
            questionMap.get(dto.getMcqId()).getChallengeOptions().add(optionDTO);
        }

        List<MultipleChoiceQuestionDTO> result = new ArrayList<>(questionMap.values());
        return ResponseEntity.ok(result);
            default:
                throw new IllegalArgumentException("Invalid question type: " + type);
        }
    }
    @Override
    public void updatePractice(Integer userId, Integer unitId,QuestionType type) {
        switch (type) {
            case MULTIPLE_CHOICE:


                List<Integer> uncompletedQuestionIds = userMultipleChoiceQuestionRepository
                .findFailedQuestionIdsByUserIdAndUnitId(userId, unitId);

        uncompletedQuestionIds.forEach(mcqId -> {
            Optional<UserMultipleChoiceQuestion> userAnswer = userMultipleChoiceQuestionRepository
                    .findByUser_UserIdAndMultipleChoiceQuestion_McqId(userId, mcqId);

            userAnswer.ifPresent(answer -> {
                if (!answer.getIsCompleted()) {
                    answer.setIsCompleted(true);
                    userMultipleChoiceQuestionRepository.save(answer);
                }
            });
        });
        break;
            default:
                throw new IllegalArgumentException("Invalid question type: " + type);
        }
    }
}
