package org.example.mediaclient.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private String url;
    private int durationSeconds;
    private CreatorDto creator;
}
