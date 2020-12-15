package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataResponseDto;
import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.User;
import com.unriviel.api.model.metadata.VideoMetaData;
import com.unriviel.api.model.metadata.review.ReviewQsAns;
import com.unriviel.api.repository.ReviewQsAnsRepository;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.ReviewService;
import com.unriviel.api.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.unriviel.api.enums.ReviewStatus.APPROVED;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final VideoMetaDataRepository metaDataRepository;
    private final ReviewQsAnsRepository ansRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    public ReviewServiceImpl(VideoMetaDataRepository metaDataRepository, ReviewQsAnsRepository ansRepository, UserService userService, ModelMapper modelMapper) {
        this.metaDataRepository = metaDataRepository;
        this.ansRepository = ansRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response onReview(ReviewQsAns reviewQsAns, String videoId) {
        var metaData = metaDataRepository.findById(videoId);
        if (metaData.isPresent()){
            var videoMetaData = metaData.get();
            if (videoMetaData.getReviewQsAns() == null) {
                var review = ansRepository.save(reviewQsAns);
                var saveMetadata = setupReviewData(videoMetaData, review);
                var responseDto = modelMapper.map(saveMetadata, VideoMetadataResponseDto.class);
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Review started",responseDto);
            }
            var qsAns = videoMetaData.getReviewQsAns();
            reviewQsAns.setId(qsAns.getId());
            var review = ansRepository.save(reviewQsAns);
            var saveMetadata = setupReviewData(videoMetaData, review);
            var responseDto = modelMapper.map(saveMetadata, VideoMetadataResponseDto.class);
           return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Updated",responseDto);
        }
      return   ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Meta data not found");
    }

    @Override
    public Response findAll(Pageable pageable) {
        var page = metaDataRepository.findAll(pageable)
                .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"Videos",page);
    }

    @Override
    public Response findAllByReviewerEmail(String email, Pageable pageable) {
        var videoMetaDataPage = metaDataRepository.findAllByReviewerEmailOrderByCreatedAtDesc(email, pageable)
                .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));

        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"Videos",videoMetaDataPage);
    }

    private VideoMetaData setupReviewData(VideoMetaData videoMetaData, ReviewQsAns review) {
        videoMetaData.setReviewQsAns(review);
        videoMetaData.reviewStatusSet();
        videoMetaData.setApprovedStatus();
        var saveMetadata = metaDataRepository.save(videoMetaData);
        var reviewStatus = saveMetadata.getReviewStatus();
        if (reviewStatus != null){
            updateUserUpload(reviewStatus,saveMetadata.getUploader());
            updateUserReview(reviewStatus,saveMetadata.getReviewer());
        }
        return saveMetadata;
    }

    private void updateUserUpload(ReviewStatus status, User user){
         if (status != null && status.equals(APPROVED)){
            user.increaseApproved();
            userService.save(user);
         }

    }
    private void updateUserReview(ReviewStatus status, User user){
        if (status != null){
              user.increaseReviewed();
              userService.save(user);
        }
    }

}