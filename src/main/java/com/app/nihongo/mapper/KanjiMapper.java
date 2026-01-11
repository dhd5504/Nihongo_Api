package com.app.nihongo.mapper;

import com.app.nihongo.dto.KanjiDTO;
import com.app.nihongo.dto.UnitDTO;
import com.app.nihongo.entity.Kanji;
import com.app.nihongo.entity.Unit;
import org.springframework.stereotype.Component;

@Component
public class KanjiMapper {
    public KanjiDTO toDto(Kanji kanji) {
        KanjiDTO kanjiDTO = new KanjiDTO();
        kanjiDTO.setId(kanji.getKanjiId());
        kanjiDTO.setCharacter(kanji.getKanjiCharacter());
        kanjiDTO.setDisplayOrder(kanji.getDisplayOrder());
        kanjiDTO.setLessonId(kanji.getKanjiLesson().getLessonId());

        return kanjiDTO;
    }
}
