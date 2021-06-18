package com.codecool.videoservice.controller;

import com.codecool.videoservice.VO.ResponseTemplateVO;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseTemplateVO getVideoWithRecommendations(@PathVariable Long id) {
        log.info("Inside getVideoWithRecommendations of VideoController.");
        return videoService.getVideoWithRecommendations(id);
    }

    @PostMapping("")
    public Video updateVideo(@RequestBody Video video) {
        log.info("Updating video !");
        videoService.updateVideo(video);
        return video;
    }
}
