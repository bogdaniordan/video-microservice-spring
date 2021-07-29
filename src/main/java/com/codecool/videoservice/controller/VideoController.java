package com.codecool.videoservice.controller;

import com.codecool.videoservice.VO.VideoWithRecommendations;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Video>> getVideos() {
        return new ResponseEntity<>(videoService.getAllVideos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoWithRecommendations> getVideoWithRecommendations(@PathVariable Long id) {
        log.info("Inside getVideoWithRecommendations of VideoController.");
        return new ResponseEntity<>(videoService.getVideoWithRecommendations(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoWithRecommendations> updateVideo(@RequestBody Video video, @PathVariable Long id) {
        log.info("Updating recommendations of video and video with id:" + video.getId());
        VideoWithRecommendations updatedVideo = videoService.updateVideo(video, id);
        return new ResponseEntity<>(updatedVideo, HttpStatus.OK);
    }
}
