package com.codecool.videoservice.controller;

import com.codecool.videoservice.VO.VideoWithRecommendations;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@Slf4j
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("")
    public List<Video> getVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public VideoWithRecommendations getVideoWithRecommendations(@PathVariable Long id) {
        log.info("Inside getVideoWithRecommendations of VideoController.");
        return videoService.getVideoWithRecommendations(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@RequestBody Video video) {
        log.info("Updating video with id:" + video.getId());
//        Video uvideoService.updateVideo(video);
//        return new ResponseEntity<>()
    }
}
