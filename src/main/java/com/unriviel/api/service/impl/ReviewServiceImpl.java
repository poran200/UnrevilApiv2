package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.ReviewStatusCount;
import com.unriviel.api.dto.VideoMetadataResponseDto;
import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.User;
import com.unriviel.api.model.metadata.VideoMetaData;
import com.unriviel.api.model.metadata.review.ReviewQsAns;
import com.unriviel.api.repository.ReviewQsAnsRepository;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.ReviewService;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.unriviel.api.enums.ReviewStatus.*;
import static com.unriviel.api.util.ResponseBuilder.*;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final VideoMetaDataRepository metaDataRepository;
    private final ReviewQsAnsRepository ansRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final VideoMetaDataService metaDataService;
    public ReviewServiceImpl(VideoMetaDataRepository metaDataRepository, ReviewQsAnsRepository ansRepository, UserService userService, ModelMapper modelMapper, VideoMetaDataService metaDataService) {
        this.metaDataRepository = metaDataRepository;
        this.ansRepository = ansRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.metaDataService = metaDataService;
    }

    @Override
    public Response onReview(ReviewQsAns reviewQsAns, String videoId) {
        var metaData = metaDataRepository.findById(videoId);
        if (metaData.isPresent()){
            var videoMetaData = metaData.get();
            if (!videoMetaData.isAssigned()){
                 return getFailureResponse(HttpStatus.BAD_REQUEST,"Video is not assigned");
            }
            if (videoMetaData.getReviewStatus() == null){
                if (videoMetaData.getReviewQsAns() == null) {
                    var review = ansRepository.save(reviewQsAns);
                    var saveMetadata = setupReviewData(videoMetaData, review);
                    var responseDto = modelMapper.map(saveMetadata, VideoMetadataResponseDto.class);
                    return getSuccessResponse(HttpStatus.OK,"Review started",responseDto);
                }
                var qsAns = videoMetaData.getReviewQsAns();
                reviewQsAns.setId(qsAns.getId());
                var review = ansRepository.save(reviewQsAns);
                var saveMetadata = setupReviewData(videoMetaData, review);
                var responseDto = modelMapper.map(saveMetadata, VideoMetadataResponseDto.class);
                return getSuccessResponse(HttpStatus.OK,"Updated",responseDto);
            }
            return getFailureResponse(HttpStatus.NOT_ACCEPTABLE,"Video Already Reviewed");

        }
      return   getFailureResponse(HttpStatus.NOT_FOUND,"Meta data not found");
    }

    @Override
    public Response findAll(Pageable pageable) {
        var page = metaDataRepository.findAll(pageable)
                .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));

        return getSuccessResponsePageWithReviewCount(HttpStatus.OK,
                        "find all videos",page,getAdminReviewStatsCount());
    }

    @Override
    public Response findAllByReviewerEmail(String email, Pageable pageable) {
        var videoMetaDataPage = metaDataRepository.findAllByReviewerEmailOrderByCreatedAtDesc(email, pageable)
                .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));

        return getSuccessResponsePageWithReviewCount(HttpStatus.OK,
                        "Videos",videoMetaDataPage,gerReviewerReviewStatsCount(email));
    }

    @Override
    public Response finAllByUploaderEmail(String email, Pageable pageable) {
        var videoMetaDataPage = metaDataRepository.findAllByUploaderEmailOrderByCreatedAtDesc(email, pageable)
                .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));

        return getSuccessResponsePageWithReviewCount(HttpStatus.OK,
                        "Videos",videoMetaDataPage,gerUploaderReviewStatsCount(email));

    }

    @Override
    public Response findAllByScorchKey(String key, Pageable pageable) {
         throw new   UnsupportedOperationException();
    }

    @Override
    public Response getMeataDataById(String videoId) {
        var meatadata = metaDataRepository.findById(videoId);
        if (meatadata.isPresent()){
            var dto = modelMapper.map(meatadata, VideoMetadataResponseDto.class);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"meta data found",dto);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"video meta data not found - "+videoId);
    }


    private VideoMetaData setupReviewData(VideoMetaData videoMetaData, ReviewQsAns review) {
        videoMetaData.setReviewQsAns(review);
        videoMetaData.reviewStatusSet();
        videoMetaData.setApprovedStatus();
        var saveMetadata = metaDataRepository.save(videoMetaData);
        var reviewStatus = saveMetadata.getReviewStatus();
        if (reviewStatus != null){
           updateUserReview(reviewStatus,saveMetadata.getReviewer());
        }
        if (reviewStatus != null && reviewStatus.equals(APPROVED)){
            updateUserUpload(reviewStatus,saveMetadata.getUploader());
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
    public ReviewStatusCount getAdminReviewStatsCount(){
        var tobereviewed = metaDataRepository.countAllByReviewProcess(TO_BE_REVIEWED);
        var inReview = metaDataRepository.countAllByReviewProcess(IN_REVIEW);
        var reviewed = metaDataRepository.countAllByReviewProcess(REVIEWED);
        return new ReviewStatusCount(tobereviewed,inReview,reviewed);
    }
    private ReviewStatusCount gerReviewerReviewStatsCount(String reviewerEmail){
        var toBeReviwe = metaDataRepository.countAllByReviewProcessAndReviewerEmail(TO_BE_REVIEWED, reviewerEmail);
        var inReview = metaDataRepository.countAllByReviewProcessAndReviewerEmail(IN_REVIEW, reviewerEmail);
        var reviewed = metaDataRepository.countAllByReviewProcessAndReviewerEmail(REVIEWED, reviewerEmail);
        return new ReviewStatusCount(toBeReviwe,inReview,reviewed);
    }
    private ReviewStatusCount gerUploaderReviewStatsCount(String uploaderEmail){
        var toBeReviwe = metaDataRepository.countAllByReviewProcessAndUploaderEmail(TO_BE_REVIEWED, uploaderEmail);
        var inReview = metaDataRepository.countAllByReviewProcessAndUploaderEmail(IN_REVIEW, uploaderEmail);
        var reviewed = metaDataRepository.countAllByReviewProcessAndUploaderEmail(REVIEWED, uploaderEmail);
        return new ReviewStatusCount(toBeReviwe,inReview,reviewed);
    }


}
