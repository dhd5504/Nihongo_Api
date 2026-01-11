package com.app.nihongo.service.question;

import com.app.nihongo.dao.FlashcardRepository;
import com.app.nihongo.dao.MultipleChoiceQuestionRepository;
import com.app.nihongo.dao.SentenceMatchingQuestionRepository;
import com.app.nihongo.dao.UserMultipleChoiceQuestionRepository;
import com.app.nihongo.dto.*;
import com.app.nihongo.entity.MultipleChoiceQuestion;
import com.app.nihongo.entity.SentenceMatchingQuestion;
import com.app.nihongo.entity.User;
import com.app.nihongo.entity.UserMultipleChoiceQuestion;
import com.app.nihongo.enums.QuestionType;
import com.app.nihongo.mapper.FlashcardMapper;
import com.app.nihongo.mapper.MultipleChoiceQuestionMapper;
import com.app.nihongo.mapper.SentenceMatchingQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SentenceMatchingQuestionRepository sentenceMatchingQuestionRepository;

    @Autowired
    private SentenceMatchingQuestionMapper sentenceMatchingQuestionMapper;

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
                            Boolean isCompleted = userMultipleChoiceQuestionRepository
                                    .existsByUser_UserIdAndMultipleChoiceQuestion_McqId(userId, mcq.getMcqId());
                            return multipleChoiceQuestionMapper.toDto(mcq, isCompleted);
                        })
                        .collect(Collectors.toList());
                return ResponseEntity.ok(mcqs);

            case SENTENCE_MATCHING:
                List<SentenceMatchingQuestion> sentenceMatchingQuestions = sentenceMatchingQuestionRepository
                        .findByLesson_LessonId(lessonId);

                // Group all questions into a single "Pair Matching" challenge
                Map<String, Object> pairingChallenge = new HashMap<>();
                pairingChallenge.put("id", lessonId); // Use lessonId as challenge id
                pairingChallenge.put("type", "PAIR_MATCHING");
                pairingChallenge.put("question", "Nối các câu tương ứng");

                List<Map<String, Object>> pairs = sentenceMatchingQuestions.stream().map(smq -> {
                    Map<String, Object> p = new HashMap<>();
                    p.put("id", smq.getSmqId());
                    p.put("japanese", smq.getJapaneseSentence());
                    p.put("vietnamese", smq.getVietnameseSentence());
                    return p;
                }).collect(Collectors.toList());

                pairingChallenge.put("pairs", pairs);
                pairingChallenge.put("completed", false);
                pairingChallenge.put("challengeOptions", new ArrayList<>()); // Empty for this type

                return ResponseEntity.ok(Collections.singletonList(pairingChallenge));

            default:
                throw new IllegalArgumentException("Invalid question type");
        }
    }

    @Override
    public List<UserFailedQuestionDTO> getFailedQuestionsByUserId(Integer userId) {
        return userMultipleChoiceQuestionRepository.findFailedMultipleChoiceQuestionsByUserId(userId);
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
                            Boolean isCompleted = userMultipleChoiceQuestionRepository
                                    .existsByUser_UserIdAndMultipleChoiceQuestion_McqId(null, mcqEntity.getMcqId());
                            return multipleChoiceQuestionMapper.toDto(mcqEntity, isCompleted);
                        })
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Multiple choice question not found with ID: " + questionId));
                return ResponseEntity.ok(mcq);

            case SENTENCE_MATCHING:
                SentenceMatchingQuestion smqEntityFound = sentenceMatchingQuestionRepository.findById(questionId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Sentence matching question not found with ID: " + questionId));
                List<SentenceMatchingQuestion> lessonQuestions = sentenceMatchingQuestionRepository
                        .findByLesson_LessonId(smqEntityFound.getLesson().getLessonId());
                SentenceMatchingQuestionDTO smq = sentenceMatchingQuestionMapper.toDto(smqEntityFound, false,
                        lessonQuestions);
                return ResponseEntity.ok(smq);

            default:
                throw new IllegalArgumentException("Invalid question type: " + type);
        }
    }

    @Override
    public void saveAnswer(Integer userId, Integer questionId, QuestionType type, boolean isCorrect) {
        switch (type) {
            case MULTIPLE_CHOICE:
                Optional<UserMultipleChoiceQuestion> existingAnswer = userMultipleChoiceQuestionRepository
                        .findByUser_UserIdAndMultipleChoiceQuestion_McqId(userId, questionId);

                if (existingAnswer.isPresent()) {
                    UserMultipleChoiceQuestion userAnswer = existingAnswer.get();
                    // Only update to true if correct. Don't lose completion status (and XP) if
                    // answered wrong later.
                    if (isCorrect && !userAnswer.getIsCompleted()) {
                        userAnswer.setIsCompleted(true);
                        userMultipleChoiceQuestionRepository.save(userAnswer);
                    }
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
                // Handle or throw if other types don't support progress tracking yet
                break;
        }
    }

    @Override
    public ResponseEntity<?> getFailedQuestionsByUnit(Integer userId, Integer unitId, QuestionType type) {
        if (type == QuestionType.MULTIPLE_CHOICE) {
            List<FailedMultipleChoiceQuestionDTO> flatQuestions = userMultipleChoiceQuestionRepository
                    .findFailedMultipleChoiceQuestionsByUserIdAndUnitId(userId, unitId);

            Map<Integer, MultipleChoiceQuestionDTO> questionMap = new HashMap<>();
            for (FailedMultipleChoiceQuestionDTO dto : flatQuestions) {
                if (!questionMap.containsKey(dto.getMcqId())) {
                    questionMap.put(dto.getMcqId(), new MultipleChoiceQuestionDTO(dto.getMcqId(),
                            dto.getQuestionContent(), dto.getIsCompleted()));
                    questionMap.get(dto.getMcqId()).setChallengeOptions(new ArrayList<>());
                }
                MultipleChoiceQuestionOptionDTO optionDTO = new MultipleChoiceQuestionOptionDTO(dto.getOptionId(),
                        dto.getOption(), dto.getIsCorrect());
                questionMap.get(dto.getMcqId()).getChallengeOptions().add(optionDTO);
            }
            return ResponseEntity.ok(new ArrayList<>(questionMap.values()));
        }
        return ResponseEntity.badRequest().body("Only MULTIPLE_CHOICE supported for failed questions by unit.");
    }

    @Override
    public void updatePractice(Integer userId, Integer unitId, QuestionType type) {
        if (type == QuestionType.MULTIPLE_CHOICE) {
            List<Integer> uncompletedQuestionIds = userMultipleChoiceQuestionRepository
                    .findFailedQuestionIdsByUserIdAndUnitId(userId, unitId);

            uncompletedQuestionIds.forEach(mcqId -> {
                userMultipleChoiceQuestionRepository
                        .findByUser_UserIdAndMultipleChoiceQuestion_McqId(userId, mcqId)
                        .ifPresent(answer -> {
                            if (!answer.getIsCompleted()) {
                                answer.setIsCompleted(true);
                                userMultipleChoiceQuestionRepository.save(answer);
                            }
                        });
            });
        }
    }
}
