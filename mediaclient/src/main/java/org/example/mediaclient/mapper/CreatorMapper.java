package org.example.mediaclient.mapper;

import org.example.mediaclient.dto.CreatorDto;
import org.example.proto.Creator;
import org.springframework.stereotype.Component;

@Component
public class CreatorMapper {
    public CreatorDto fromProtoToDto(Creator creator) {
        return CreatorDto.builder()
                .id(creator.getId())
                .name(creator.getName())
                .email(creator.getEmail())
                .build();
    }
}
