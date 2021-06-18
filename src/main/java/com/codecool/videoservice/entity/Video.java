package com.codecool.videoservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String url;
    @ElementCollection
    private List<Long> recommendationsId;
}
