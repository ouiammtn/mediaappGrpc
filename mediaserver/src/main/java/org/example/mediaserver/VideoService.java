package org.example.mediaserver;

import net.devh.boot.grpc.server.service.GrpcService;
import org.example.mediaserver.dao.VideoRepository;
import org.example.mediaserver.dao.CreatorRepository;
import org.example.mediaserver.model.CreatorEntity;
import org.example.mediaserver.model.VideoEntity;
import org.example.proto.*;
import io.grpc.stub.StreamObserver;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GrpcService
public class VideoService extends VideoServiceGrpc.VideoServiceImplBase {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CreatorRepository creatorRepository; // Correct injection

    @Override
    public void uploadVideo(UploadVideoRequest request, StreamObserver<Video> responseObserver) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(UUID.randomUUID().toString());
        videoEntity.setTitle(request.getTitle());
        videoEntity.setDescription(request.getDescription());
        videoEntity.setUrl(request.getUrl());
        videoEntity.setDurationSeconds(request.getDurationSeconds());
        videoEntity.setCreatorId(request.getCreator().getId());

        videoRepository.save(videoEntity);

        Video video = Video.newBuilder()
                .setId(videoEntity.getId())
                .setTitle(videoEntity.getTitle())
                .setDescription(videoEntity.getDescription())
                .setUrl(videoEntity.getUrl())
                .setDurationSeconds(videoEntity.getDurationSeconds())
                .setCreator(request.getCreator())
                .build();

        responseObserver.onNext(video);
        responseObserver.onCompleted();
    }

    @Override
    public void getVideo(VideoIdRequest request, StreamObserver<Video> responseObserver) {
        VideoEntity videoEntity = videoRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Video not found"));

        CreatorEntity creatorEntity = creatorRepository.findById(videoEntity.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found")); // Fixed CreatorRepository usage

        Creator creator = Creator.newBuilder()
                .setId(creatorEntity.getId())
                .setName(creatorEntity.getName())
                .setEmail(creatorEntity.getEmail())
                .build();

        Video video = Video.newBuilder()
                .setId(videoEntity.getId())
                .setTitle(videoEntity.getTitle())
                .setDescription(videoEntity.getDescription())
                .setUrl(videoEntity.getUrl())
                .setDurationSeconds(videoEntity.getDurationSeconds())
                .setCreator(creator)
                .build();

        responseObserver.onNext(video);
        responseObserver.onCompleted();
    }
}
