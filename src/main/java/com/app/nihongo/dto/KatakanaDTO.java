package com.app.nihongo.dto;

import lombok.Data;

@Data
public class KatakanaDTO {
    private Integer id;
    private String character;
    private String romaji;
    private Integer displayOrder;
}
