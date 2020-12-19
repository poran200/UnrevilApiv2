package com.unriviel.api.service.impl;

import com.unriviel.api.dto.MetaDataFilterRequest;
import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataResponseDto;
import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.MataDataFilterService;
import com.unriviel.api.service.ReviewService;
import com.unriviel.api.util.ResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.unriviel.api.util.ResponseBuilder.getSuccessResponsePage;
@Service
public class MataDataFilterServiceImpl implements MataDataFilterService {
    private final VideoMetaDataRepository metaDataRepository;
    private final ModelMapper modelMapper;
    private final ReviewService reviewService;
    public MataDataFilterServiceImpl(VideoMetaDataRepository metaDataRepository, ModelMapper modelMapper, ReviewService reviewService) {
        this.metaDataRepository = metaDataRepository;
        this.modelMapper = modelMapper;
        this.reviewService = reviewService;
    }

    @Override
    public Response filterMetaDataByTiterOrEmailOrNameOrFullName(MetaDataFilterRequest req, Pageable pageable) {
        Response response = null;
        //if not and admin
        if (!isLoginUserIsAdmin(req)) {
            response = reviewService.findAllByReviewerEmail(req.getLoggedUser(), pageable);
            // search key and last activity and process
        } else if (isLoginUserIsAdmin(req) && !isScarceKeyIsEmpty(req) &&
                !isActivelyDayIsZero(req) && getReviewProcess(req.getReviewProcess()) != null) {
            response = findAllSearchKeyAndActivityAndProcess(req.getSearchBy(), today(), getBeforeDate(req.getLastActivity()), getReviewProcess(req.getReviewProcess()), pageable);

        }
        // only scarch by                                                    / 0                         / null
        else if (isLoginUserIsAdmin(req) && !isScarceKeyIsEmpty(req) && isActivelyDayIsZero(req) && getReviewProcess(req.getReviewProcess()) == null) {
            response = findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullName(req.getSearchBy(), pageable);
            // search key and review process
        } else if (isLoginUserIsAdmin(req) && !isScarceKeyIsEmpty(req) && getReviewProcess(req.getReviewProcess()) != null && isActivelyDayIsZero(req)) {
            response = findAllBySearchKeyAndReviewProcess(req.getSearchBy(), getReviewProcess(req.getReviewProcess()), pageable);
            //search key and last activity
        } else if (isLoginUserIsAdmin(req) && !isScarceKeyIsEmpty(req) && !isActivelyDayIsZero(req)) {
            response = findAllSearchKeyAndLastActivity(req.getSearchBy(), today(), getBeforeDate(req.getLastActivity()), pageable);
            // last activity and review
        } else if (isLoginUserIsAdmin(req) && !isActivelyDayIsZero(req) && getReviewProcess(req.getReviewProcess()) != null) {
            response = findAllLastActivityAndRevileProcess(getReviewProcess(req.getReviewProcess()), today(), getBeforeDate(req.getLastActivity()), pageable);

        //  only last activity
        } else if (isLoginUserIsAdmin(req) && !isActivelyDayIsZero(req) && getReviewProcess(req.getReviewProcess()) == null && isScarceKeyIsEmpty(req)){
               response = findAllByLastActivity(today(),getBeforeDate(req.getLastActivity()),pageable);
        // all filed are blank
        }  else if (isLoginUserIsAdmin(req)&& isScarceKeyIsEmpty(req)&& isActivelyDayIsZero(req) && getReviewProcess(req.getReviewProcess()) == null ){
             response = reviewService.findAll(pageable);
        }else if (isLoginUserIsAdmin(req) && isScarceKeyIsEmpty(req)&& isActivelyDayIsZero(req) && getReviewProcess(req.getReviewProcess())!= null){
            response = findAllByReview(getReviewProcess(req.getReviewProcess()),pageable);
        }
         return response;

   }

    public Response findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullName(String key, Pageable pageable) {
        var page = metaDataRepository.findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithOrderByCreatedAtDesc(key, key, key, key, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));

        return getSuccessResponsePage(HttpStatus.OK,"filter apply by search key ",page);
    }
    public Response findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullNameAndUploader(String key,String uploaderEmail, Pageable pageable) {
        var page = metaDataRepository.findAllByTitleOrReviewerUsernameOrReviewerEmailOrReviewerFullNameAndUploaderEmailOrderByCreatedAtDesc(key, key, key, key,uploaderEmail, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));

        return getSuccessResponsePage(HttpStatus.OK,"filter Data",page);
    }
    private Response findAllSearchKeyAndActivityAndProcess(String key,Instant today,Instant  beforeAgo,ReviewStatus process,Pageable pageable){
        var page =  metaDataRepository.findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithAndCreatedAtBetweenAndReviewProcess(key,key,key,key,today,beforeAgo,process,pageable)
                 .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));
         return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"filter apply on search  key and review process and last activity ",page);

    }
    private Response findAllBySearchKeyAndReviewProcess(String key,ReviewStatus process,Pageable pageable){
        var page = metaDataRepository.findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithAndReviewProcessOrderByCreatedAtDesc(key, key, key, key, process, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));
        return getSuccessResponsePage(HttpStatus.OK,"filter apply on search key and review process",page);
    }
    private Response findAllSearchKeyAndLastActivity(String key,Instant today,Instant beforeAgo,Pageable pageable){
        var page = metaDataRepository.findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithAndCreatedAtBetween(key, key, key, key, today, beforeAgo, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"filter apply on search key and activity",page);


    }
    private Response findAllLastActivityAndRevileProcess(ReviewStatus process,Instant today,Instant beforeDay,Pageable pageable){
        var page = metaDataRepository.findAllByReviewProcessAndCreatedAtBetween(process, today, beforeDay, pageable)
                     .map(metaData -> modelMapper.map(metaData,VideoMetadataResponseDto.class));
       return getSuccessResponsePage(HttpStatus.OK,"filter apply by activity and process ",page);
    }
    private Response findAllByLastActivity(Instant today,Instant dayBefore,Pageable pageable){
        var page = metaDataRepository.findAllByCreatedAtBetween(today, dayBefore, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));
        return getSuccessResponsePage(HttpStatus.OK,"filter apply by activity",page);
    }
    private Response findAllByReview(ReviewStatus process,Pageable pageable){
        var pa = metaDataRepository.findAllByReviewProcess(process, pageable).map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));
      return   getSuccessResponsePage(HttpStatus.OK,"filter apply by review process",pa);
    }
    private boolean isAllFiledBlank(MetaDataFilterRequest req){
        return isScarceKeyIsEmpty(req)&&isDateIsEmpty(req)&&isActivelyDayIsZero(req) && isUploaderEmailIsEmpty(req);
    }
    private  boolean isLoginUserIsAdmin(MetaDataFilterRequest req){
         return req.getLoggedUser().equals("admin@gmail.com");
    }
    private boolean isScarceKeyIsEmpty(MetaDataFilterRequest req){
        return  req.getSearchBy().isBlank() || req.getUploader() == null;
    }
    private  boolean isDateIsEmpty(MetaDataFilterRequest req){
         return req.getSortBy() == null;
    }
    private  boolean isActivelyDayIsZero(MetaDataFilterRequest request){
       return request.getLastActivity() == 0;
    }
    private boolean isUploaderEmailIsEmpty(MetaDataFilterRequest req){
         return  req.getUploader().isBlank() || req.getUploader() == null;
    }
    private ReviewStatus getReviewProcess(int reviewPores){
        if (reviewPores == 0) return null;
        if (reviewPores == 1) return ReviewStatus.TO_BE_REVIEWED;
        if (reviewPores == 2) return ReviewStatus.IN_REVIEW;
        if (reviewPores == 3) return ReviewStatus.REVIEWED;
        return  null;
    }
    private Instant getBeforeDate(int lastDay){
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -lastDay);
        return cal.getTime().toInstant();
    }
    private  Instant today(){
        return new GregorianCalendar().getTime().toInstant();
    }

}
