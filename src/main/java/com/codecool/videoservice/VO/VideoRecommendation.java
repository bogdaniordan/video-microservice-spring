package com.codecool.videoservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoRecommendation {
    private Long id;
    private int rating;
    private String comment;
    private Long videoId;
    private LocalDate date = LocalDate.now();
}
