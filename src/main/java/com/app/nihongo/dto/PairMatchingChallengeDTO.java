package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PairMatchingChallengeDTO {
    private Integer id;
    private String type; // PAIR_MATCHING
    private String question; // "Match the pairs"
    private List<PairDTO> pairs;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PairDTO {
        private String japanese;
        private String vietnamese;
    }
}
