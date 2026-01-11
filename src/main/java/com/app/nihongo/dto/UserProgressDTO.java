package com.app.nihongo.dto;

import lombok.Data;

@Data
public class UserProgressDTO {
    private Integer progressId;
    private Integer userId;
    private Integer lessonId;
    private Integer score;
}
