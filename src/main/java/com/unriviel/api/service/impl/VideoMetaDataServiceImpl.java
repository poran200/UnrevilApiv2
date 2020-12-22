package com.unriviel.api.service.impl;

import com.unriviel.api.dto.*;
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
    public Response save(VideoMetadataRequestDto metadataDto) {
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
            var metadataDto = modelMapper.map(optionalVideoMetaData.get(), VideoMetadataRequestDto.class);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Video meta data  found",metadataDto);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                "Video metadata not found [id]="+videoId);
    }
    @Override
    public Response findByVideoIdReview(String videoId) {
        var optionalVideoMetaData = videoMetaDataRepository.findById(videoId);
        if (optionalVideoMetaData.isPresent()){
            var metadataDto = modelMapper.map(optionalVideoMetaData.get(), VideoMetadataResponseDto.class);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Video meta data  found",metadataDto);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                "Video metadata not found [id]="+videoId);
    }

    @Override
    public Response finByUserEmail(String email, Pageable pageable) {
        var page = videoMetaDataRepository.findAllByUploaderEmailOrderByCreatedAtDesc(email, pageable)
                .map(metaData -> modelMapper.map(metaData,VideoMetadataRequestDto.class));
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
        var videoMetaData = videoMetaDataRepository.findById(response.getVideoId());
        if (videoMetaData.isPresent()){
            var data = videoMetaData.get();
            data.setVideoId(response.getVideoId());
            data.setVideoUrl(response.getUrl());
            data.setUploaded(response.isUploaded());
            userOptional.ifPresent(data::setUploader);
            if (response.isUploaded()){
                userOptional.ifPresent(User::increaseUpload);
                data.setReviewProcess(ReviewStatus.TO_BE_REVIEWED);
                userOptional.ifPresent(userService::save);
            }

            videoMetaDataRepository.save(data);
        }else {
            metaData.setVideoId(response.getVideoId());
            metaData.setVideoUrl(response.getUrl());
            metaData.setUploaded(response.isUploaded());
            userOptional.ifPresent(metaData::setUploader);
            if (response.isUploaded()) {
                userOptional.ifPresent(User::increaseUpload);
                metaData.setReviewProcess(ReviewStatus.TO_BE_REVIEWED);
                userOptional.ifPresent(userService::save);
            }
            videoMetaDataRepository.save(metaData);
        }

    }

    @Override
    public boolean isExistById(String videoId) {
        return videoMetaDataRepository.existsById(videoId);
    }

    @Override
    public Response update(String videoId, VideoMetadataRequestDto videoMetadataRequestDto) {
        var metaData = modelMapper.map(videoMetadataRequestDto, VideoMetaData.class);
        var optionalVideoMetaData = videoMetaDataRepository.findById(videoId);
        if (optionalVideoMetaData.isPresent()){
            var updated = optionalVideoMetaData.get();
             metaData.setVideoUrl(updated.getVideoUrl());
             metaData.setReviewStatus(updated.getReviewStatus());
             metaData.setUploaded(updated.isUploaded());
             metaData.setUploader(updated.getUploader());
             metaData.setReviewer(updated.getReviewer());
             metaData.setReviewProcess(updated.getReviewProcess());
             metaData.setAssigned(updated.isAssigned());
             metaData.setApprovedAt(updated.getApprovedAt());
             metaData.setAssignedAt(updated.getAssignedAt());
             metaData.setReviewQsAns(updated.getReviewQsAns());

            var save = videoMetaDataRepository.save(metaData);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Updated",modelMapper.map(save, VideoMetadataRequestDto.class));
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"video not found");
    }

    @Override
    public Response saveWithExternalUrl(VideoExternalUrlRequest request,String email) {
        var metaData = videoMetaDataRepository.findById(request.getVideoId());
        if (metaData.isPresent()){
            var optionalUser = userService.findByEmail(email);
            metaData.get().setExternalVideoUrl(request.getUrl());
            metaData.get().setFetchedFromUrl(false);
            optionalUser.ifPresent(user ->  metaData.get().setUploader(user));
            videoMetaDataRepository.save(metaData.get());
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Url save Successfully",null);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,
                "VideoId not found= "+request.getVideoId());
    }

    @Override
    public void saveVideoInfo(String videoId,VideoInfo videoInfo) {
        var metaData = videoMetaDataRepository.findById(videoId);
         if (metaData.isPresent()){
             var data = metaData.get();
             if (videoInfo.isValid()) {
                 data.setVideoName(videoInfo.getVideoName());
                 data.setVideoType(videoInfo.getVideoType());
                 data.setVideoUrl(videoInfo.getVideoUrl());
                 data.setVideoDuration(videoInfo.getVideoDuration());
                 data.setVideoSize(videoInfo.getVideoSize());
                 data.setVideoFps(videoInfo.getVideoFps());
                 data.setFetchStatus(VideoInfo.VIDEO_SAVED);
                 data.setFetchedFromUrl(true);
                 data.setReviewProcess(ReviewStatus.TO_BE_REVIEWED);
             }else {
                 data.setFetchedFromUrl(false);
                 data.setFetchStatus(videoInfo.getStatusMassage());
                 videoMetaDataRepository.save(data);
             }
             videoMetaDataRepository.save(data);
         }
    }
}
