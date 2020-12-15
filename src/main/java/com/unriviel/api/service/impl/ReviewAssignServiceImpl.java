package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataResponseDto;
import com.unriviel.api.model.User;
import com.unriviel.api.repository.UserRepository;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.ReviewAssignService;
import com.unriviel.api.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Log4j2
public class ReviewAssignServiceImpl implements ReviewAssignService {
    private final VideoMetaDataRepository videoMetaDataRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public ReviewAssignServiceImpl(VideoMetaDataRepository videoMetaDataRepository,
                                   UserRepository userRepository, ModelMapper modelMapper) {
        this.videoMetaDataRepository = videoMetaDataRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public Response assign(String videoId, String reviewerEmail) {
        var metaData = videoMetaDataRepository.findById(videoId);
        if (metaData.isPresent()){
            var reviewer = userRepository.findByEmail(reviewerEmail);
            if (reviewer.isPresent()) {
                metaData.get().setReviewer(reviewer.get());
                metaData.get().setAssigned(true);
                metaData.get().setAssignedAt(new Date());
                reviewer.ifPresent(User::increaseAssigned);
                var save = userRepository.save(reviewer.get());
                var videoMetaData = videoMetaDataRepository.save(metaData.get());

                var totalAssigned = save.getTotalAssigned();
                log.info("totalAssigned = " + totalAssigned);
                var metadataDto = modelMapper.map(videoMetaData, VideoMetadataResponseDto.class);
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"video assign successfully!",metadataDto);
            }
            return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Reviewer not found! [email]="+reviewerEmail);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"video not found! [id]="+videoId);
    }
}
