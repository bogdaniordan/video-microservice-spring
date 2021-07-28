package com.codecool.videoservice.VO;

import com.codecool.videoservice.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVO {
    private Video video;
    private List<VideoRecommendation> videoRecommendations;
}
