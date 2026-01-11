package com.app.nihongo.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnitDTO {
    private Integer id;
    private Integer displayOrder;
    private String title;
    private String description;
    private String level;
    private List<LessonDTO> lessons;
}
