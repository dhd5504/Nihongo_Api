package com.app.nihongo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserLessonStatusKey implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "lesson_id")
    private Integer lessonId;
}
