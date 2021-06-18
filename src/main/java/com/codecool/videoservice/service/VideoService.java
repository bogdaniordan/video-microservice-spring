package com.codecool.videoservice.service;

import com.codecool.videoservice.VO.ResponseTemplateVO;
import com.codecool.videoservice.VO.VideoRecommendation;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Video> getAllVideos() {
        log.info("Fetching all videos.");
        return videoRepository.findAll();
    }

    public ResponseTemplateVO getVideoWithRecommendations(Long id) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Video video = videoRepository.findById(id).get();
        List<VideoRecommendation> videoRecommendations = new ArrayList<>();
        for(Long recommendationId: video.getRecommendationsId()) {
            videoRecommendations.add(restTemplate.getForObject("http://localhost:9002/video-recommendations/" + recommendationId, VideoRecommendation.class));
        }
        vo.setVideo(video);
        vo.setVideoRecommendations(videoRecommendations);
        return vo;
    }

    public void updateVideo(Video video) {
        List<Long> newRecommendationsIds = video.getRecommendationsId();
        for (Long id: newRecommendationsIds) {
            VideoRecommendation newVideoRecommendation = restTemplate.getForObject("http://localhost:9002/video-recommendations/" + id, VideoRecommendation.class);
            VideoRecommendation updatedVideoRecommendation = new VideoRecommendation(newVideoRecommendation.getId(),
                    newVideoRecommendation.getRating(), newVideoRecommendation.getComment(), video.getId());
            restTemplate.put("http://localhost:9002/video-recommendations/", updatedVideoRecommendation);
        }
        videoRepository.save(video);
    }
}

