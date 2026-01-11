package com.app.nihongo.dto;

import lombok.Data;

@Data
public class KanjiDTO {
    private Integer id;
    private String character;

    private Integer displayOrder;
    private Integer lessonId;
}
