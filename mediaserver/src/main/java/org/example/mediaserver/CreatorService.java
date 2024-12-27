package org.example.mediaserver;

import net.devh.boot.grpc.server.service.GrpcService;
import org.example.mediaserver.dao.VideoRepository;
import org.example.mediaserver.dao.CreatorRepository;
import org.example.mediaserver.model.CreatorEntity;
import org.example.proto.*;
import io.grpc.stub.StreamObserver;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@GrpcService
public class CreatorService extends CreatorServiceGrpc.CreatorServiceImplBase {

    @Autowired
    private CreatorRepository creatorRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public void getCreator(CreatorIdRequest request, StreamObserver<Creator> responseObserver) {
        CreatorEntity creatorEntity = creatorRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Creator creator = Creator.newBuilder()
                .setId(creatorEntity.getId())
                .setName(creatorEntity.getName())
                .setEmail(creatorEntity.getEmail())
                .build();

        responseObserver.onNext(creator);
        responseObserver.onCompleted();
    }

    @Override
    public void getCreatorVideos(CreatorIdRequest request, StreamObserver<VideoStream> responseObserver) {
        var videos = videoRepository.findAllByCreatorId(request.getId()).stream()
                .map(videoEntity -> Video.newBuilder()
                        .setId(videoEntity.getId())
                        .setTitle(videoEntity.getTitle())
                        .setDescription(videoEntity.getDescription())
                        .setUrl(videoEntity.getUrl())
                        .setDurationSeconds(videoEntity.getDurationSeconds())
                        .build())
                .collect(Collectors.toList());

        VideoStream videoStream = VideoStream.newBuilder()
                .addAllVideos(videos)
                .build();

        responseObserver.onNext(videoStream);
        responseObserver.onCompleted();
    }
}