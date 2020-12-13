package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataRequestDto;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.ReviewAssignService;
import com.unriviel.api.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReviewAssignServiceImpl implements ReviewAssignService {
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    public ReviewAssignServiceImpl(VideoMetaDataRepository videoMetaDataRepository,
                                   UserService userService, ModelMapper modelMapper) {
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response assign(String videoId, String reviewerEmail) {
        var metaData = videoMetaDataRepository.findById(videoId);
        if (metaData.isPresent()){
            var reviewer = userService.findByEmail(reviewerEmail);
            if (reviewer.isPresent()) {
                metaData.get().setReviewer(reviewer.get());
                metaData.get().setUnassigned(true);
                var videoMetaData = videoMetaDataRepository.save(metaData.get());
                var metadataDto = modelMapper.map(videoMetaData, VideoMetadataRequestDto.class);
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"video assign successfully!",metadataDto);
            }
            return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Reviewer not found! [email]="+reviewerEmail);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"video not found! [id]="+videoId);
    }
}
