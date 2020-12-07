package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataDto;
import com.unriviel.api.model.metadata.VideoMetaData;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class VideoMetaDataServiceImpl implements VideoMetaDataService {
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final ModelMapper modelMapper;

    public VideoMetaDataServiceImpl(VideoMetaDataRepository videoMetaDataRepository, ModelMapper modelMapper) {
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Response save(VideoMetadataDto metadataDto) {
        var videoMetaData = modelMapper.map(metadataDto, VideoMetaData.class);
        var existsById = videoMetaDataRepository.existsById(videoMetaData.getVideoId());
        if (existsById) {
            log.info("the video id exist");
            return ResponseBuilder.getFailureResponse(HttpStatus.CONFLICT,
                    "the Video id already Exists [videoId] = " + metadataDto.getVideoId());
        } else
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
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Video meta data  found",optionalVideoMetaData.get());
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                "Video metadata not found [id]="+videoId);
    }

    @Override
    public Response finByUserEmail(String email, Pageable pageable) {
//        var page = videoMetaDataRepository.findAllByUserEmailAndOrderByCreatedAtDesc(email, pageable);
//        if (page.hasContent()){
//            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"Videos",page);
//        }
//        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"No contend found");
        return null;
    }

    @Override
    public Response approvedVideo(String videoId, boolean isApproved, String reviewedBy) {
        return null;
    }
}
