package com.app.nihongo.dto;

import lombok.Data;

@Data
public class HiraganaDTO {
    private Integer id;
    private String character;
    private String romaji;
    private Integer displayOrder;
}
