package org.example.mediaclient.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.mediaclient.dto.CreatorDto;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.mapper.CreatorMapper;
import org.example.mediaclient.mapper.VideoMapper;
import org.example.proto.CreatorIdRequest;
import org.example.proto.CreatorServiceGrpc;
import org.example.proto.VideoStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreatorService {

    @GrpcClient("mediaserver")
    private CreatorServiceGrpc.CreatorServiceBlockingStub creatorStub;

    @Autowired
    private CreatorMapper creatorMapper;

    @Autowired
    private VideoMapper videoMapper;

    public CreatorDto getCreator(String id) {
        return creatorMapper.fromProtoToDto(
                creatorStub.getCreator(CreatorIdRequest.newBuilder().setId(id).build())
        );
    }

    public List<VideoDto> getCreatorVideos(String id) {
        VideoStream videoStream = creatorStub.getCreatorVideos(CreatorIdRequest.newBuilder().setId(id).build());
        return videoStream.getVideosList().stream()
                .map(videoMapper::fromVideoProtoToVideo)
                .collect(Collectors.toList());
    }
}
