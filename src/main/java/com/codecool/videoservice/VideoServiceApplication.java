package com.codecool.videoservice;

import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.repository.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class VideoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	CommandLineRunner initDatabase(VideoRepository repository) {
		return args -> {
			repository.save(new Video(1L, "salmon", "www.youtube.com", Arrays.asList(1L, 2L ,3L)));
			repository.save(new Video(2L, "tuna", "www.tuna.com", Arrays.asList(4L, 5L ,6L)));
			repository.save(new Video(3L, "pickle", "www.facebook.com", Arrays.asList(7L, 8L ,9L)));
		};
	}

}
