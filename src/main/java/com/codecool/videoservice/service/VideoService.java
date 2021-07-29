package com.codecool.videoservice.service;

import com.codecool.videoservice.VO.VideoWithRecommendations;
import com.codecool.videoservice.VO.VideoRecommendation;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    public VideoWithRecommendations getVideoWithRecommendations(Long id) {
        VideoWithRecommendations videoWithRecommendations = new VideoWithRecommendations();
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not find video with id: " + id));
        List<VideoRecommendation> videoRecommendations = new ArrayList<>();
        for(Long recommendationId: video.getRecommendationsId()) {
            videoRecommendations.add(restTemplate.getForObject("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/" + recommendationId, VideoRecommendation.class));
        }
        videoWithRecommendations.setVideo(video);
        videoWithRecommendations.setVideoRecommendations(videoRecommendations);
        return videoWithRecommendations;
    }

    public VideoWithRecommendations updateVideo(Video video, Long id) {
        Video updatedVideo = updateVideoInDB(video, id);

        List<VideoRecommendation> updatedRecommendations = updateVideoRecommendations(updatedVideo);

        VideoWithRecommendations videoWithRecommendations = new VideoWithRecommendations();
        videoWithRecommendations.setVideo(updatedVideo);
        videoWithRecommendations.setVideoRecommendations(updatedRecommendations);
        return videoWithRecommendations;
//        List<Long> newRecommendationsIds = video.getRecommendationsId();
//        for (Long id: newRecommendationsIds) {
//            VideoRecommendation newVideoRecommendation = restTemplate.getForObject("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/" + id, VideoRecommendation.class);
//            if (newVideoRecommendation != null) {
//                VideoRecommendation updatedVideoRecommendation = new VideoRecommendation(newVideoRecommendation.getId(),
//                        newVideoRecommendation.getRating(), newVideoRecommendation.getComment(), video.getId());
//                restTemplate.put("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/", updatedVideoRecommendation);
//            }
//        }
//        videoRepository.save(video);
    }

    private Video updateVideoInDB(Video video, Long id) {
        Video updatedVideo = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not find video with id: "+ id));
        updatedVideo.setName(video.getName());
        updatedVideo.setUrl(updatedVideo.getUrl());
        updatedVideo.setRecommendationsId(video.getRecommendationsId());
        return videoRepository.save(updatedVideo);
    }

    private List<VideoRecommendation> updateVideoRecommendations(Video updatedVideo) {
        List<VideoRecommendation> videoRecommendations = new ArrayList<>();
        updatedVideo.getRecommendationsId().forEach( recommendationId -> {
            VideoRecommendation videoRecommendation = restTemplate.getForObject("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/" + recommendationId, VideoRecommendation.class);
            if (videoRecommendation != null) {
                videoRecommendation.setVideoId(updatedVideo.getId());
                restTemplate.put("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/", videoRecommendation);
                videoRecommendations.add(videoRecommendation);
            }
        });
        return videoRecommendations;
    }

}

