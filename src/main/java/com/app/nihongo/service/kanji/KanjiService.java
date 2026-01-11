package com.app.nihongo.service.kanji;

import com.app.nihongo.dto.KanjiDTO;
import java.util.List;

public interface KanjiService {
    List<KanjiDTO> getKanjiByLessonId(Integer kanjiLessonId);
}
