package com.app.nihongo.dto;

import lombok.Data;

@Data
public class LessonDTO {
    private Integer id;
    private Integer order;
    private String name;
    private String type;
    private String status;

}
