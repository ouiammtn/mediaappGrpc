package org.example.mediaclient.mapper;

import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.VideoDto;
import org.example.proto.Creator;
import org.example.proto.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public VideoDto fromVideoProtoToVideo(Video video) {
        return VideoDto.builder()
            .id(video.getId())
            .title(video.getTitle())
            .description(video.getDescription())
            .url(video.getUrl())
            .durationSeconds(video.getDurationSeconds())
            .creator(video.hasCreator() ? new CreatorMapper().fromProtoToDto(video.getCreator()) : null)
            .build();
    }
}
