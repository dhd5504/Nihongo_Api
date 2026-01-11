package com.app.nihongo.service.kanji;

import com.app.nihongo.dao.KanjiRepository;
import com.app.nihongo.dto.KanjiDTO;
import com.app.nihongo.entity.Kanji;
import com.app.nihongo.mapper.KanjiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KanjiServiceImpl implements KanjiService {

    @Autowired
    private KanjiRepository kanjiRepository;

    @Autowired
    private KanjiMapper kanjiMapper;

    @Override
    public List<KanjiDTO> getKanjiByLessonId(Integer kanjiLessonId) {
        List<Kanji> kanjis = kanjiRepository.findByKanjiLesson_LessonId(kanjiLessonId);
        return kanjis.stream().map(kanjiMapper::toDto).collect(Collectors.toList());
    }
}
