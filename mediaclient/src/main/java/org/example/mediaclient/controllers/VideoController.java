package org.example.mediaclient.controllers;

import lombok.AllArgsConstructor;
import org.example.mediaclient.dto.VideoDto;
import org.example.mediaclient.service.VideoServiceClient;
import org.example.proto.Creator;
import org.example.proto.UploadVideoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/videos")
public class VideoController {


    private VideoServiceClient videoService;

    @PostMapping("addVideo")
    public ResponseEntity<VideoDto> uploadVideo() {
        System.out.println("Hello from controller ");
        Creator creator = Creator.newBuilder()
                .setName("Xproce")
                .setEmail("hirchoua.badr@gmail.com")
                .setId("f9741412-6ca1-4a47-9fc4-aaba421597e2")
                .build();

        UploadVideoRequest request = UploadVideoRequest.newBuilder()
                .setTitle("grpc 101")
                .setDescription("The gRPC 101 is an introductory course to master Grpc")
                .setUrl("https://github.com/badrhr/gRPC101")
                .setDurationSeconds(380)
                .setCreator(creator)
                .build();

        System.out.println(request);

        VideoDto videoDto = videoService.uploadVideo(request);
        System.out.println(videoDto);
        return ResponseEntity.ok(videoDto) ;
    }
}