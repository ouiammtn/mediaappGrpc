package org.example.mediaclient.service;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.mediaclient.dto.VideoDto;
import org.example.proto.UploadVideoRequest;
import org.example.proto.Video;
import org.example.proto.VideoServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.mediaclient.mapper.VideoMapper;


@Service
public class VideoServiceClient {

    @GrpcClient("mediaserver")
    private VideoServiceGrpc.VideoServiceBlockingStub stub;

    @Autowired
    private VideoMapper mapper;

    public VideoDto uploadVideo(UploadVideoRequest videoRequest) {
        System.out.println("Hello from client service with data : "+ videoRequest);

        Video video = stub.uploadVideo(videoRequest);
        return mapper.fromVideoProtoToVideo(video);
    }
}