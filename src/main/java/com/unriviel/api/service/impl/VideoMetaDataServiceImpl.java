package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataDto;
import com.unriviel.api.dto.VideoResponse;
import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.User;
import com.unriviel.api.model.metadata.VideoMetaData;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class VideoMetaDataServiceImpl implements VideoMetaDataService {
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public VideoMetaDataServiceImpl(VideoMetaDataRepository videoMetaDataRepository, ModelMapper modelMapper, UserService userService) {
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public Response save(VideoMetadataDto metadataDto) {
        var videoMetaData = modelMapper.map(metadataDto, VideoMetaData.class);
//        var existsById = videoMetaDataRepository.existsById(videoMetaData.getVideoId());
//        if (existsById) {
//            log.info("the video id exist");
//            return ResponseBuilder.getFailureResponse(HttpStatus.CONFLICT,
//                    "the Video id already Exists [videoId] = " + metadataDto.getVideoId());
//        } else
            try {
                var saveMetadata = videoMetaDataRepository.save(videoMetaData);
                if (saveMetadata != null) {
                    return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,
                            "video metadata create  successfully", saveMetadata);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error(e.getLocalizedMessage());

            }
        return ResponseBuilder.getInternalServerError();

    }

    @Override
    public Response findByVideoId(String videoId) {
        var optionalVideoMetaData = videoMetaDataRepository.findById(videoId);
        if (optionalVideoMetaData.isPresent()){
            var metadataDto = modelMapper.map(optionalVideoMetaData.get(), VideoMetadataDto.class);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Video meta data  found",metadataDto);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                "Video metadata not found [id]="+videoId);
    }

    @Override
    public Response finByUserEmail(String email, Pageable pageable) {
        var page = videoMetaDataRepository.findAllByUploaderEmail(email, pageable);
        if (page.hasContent()){
            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"Videos",page);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"No contend found");
    }

    @Override
    public Response approvedVideo(String videoId, boolean isApproved, String reviewedBy) {
        return null;
    }

    @Override
    public void saveVideoStatus(VideoResponse response) {
        Optional<User> userOptional = Optional.empty();
        if (response.getUserEmail() != null){
            userOptional=  userService.findByEmail(response.getUserEmail());
        }

        VideoMetaData metaData = new VideoMetaData();
        metaData.setVideoId(response.getVideoId());
        metaData.setVideoUrl(response.getUrl());
        metaData.setUploaded(response.isUploaded());
        userOptional.ifPresent(metaData::setUploader);
        metaData.setReviewStatus(ReviewStatus.TO_BE_REVIEWED);
        var videoMetaData = videoMetaDataRepository.findById(response.getVideoId());
        if (videoMetaData.isPresent()){
            var data = videoMetaData.get();
            data.setVideoId(response.getVideoId());
            data.setVideoUrl(response.getUrl());
            data.setUploaded(response.isUploaded());
            userOptional.ifPresent(data::setUploader);
            data.setReviewStatus(ReviewStatus.TO_BE_REVIEWED);
            videoMetaDataRepository.save(data);
        }else {
            videoMetaDataRepository.save(metaData);
        }
    }

    @Override
    public boolean isExistById(String videoId) {
        return videoMetaDataRepository.existsById(videoId);
    }

    @Override
    public Response update(String videoId, VideoMetadataDto videoMetadataDto) {
        var metaData = modelMapper.map(videoMetadataDto, VideoMetaData.class);
        var optionalVideoMetaData = videoMetaDataRepository.findById(videoId);
        if (optionalVideoMetaData.isPresent()){
            var videoId1 = optionalVideoMetaData.get().getVideoId();
            metaData.setVideoId(videoId1);
            var save = videoMetaDataRepository.save(metaData);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Updated",modelMapper.map(save,VideoMetadataDto.class));
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"video not found");
    }
}
