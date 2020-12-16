package com.unriviel.api.service.impl;

import com.unriviel.api.dto.MetaDataFilterRequest;
import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataResponseDto;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.MataDataFilterService;
import com.unriviel.api.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
         if (!isLoginUserIsAdmin(req)){
           response =  reviewService.findAllByReviewerEmail(req.getLoggedUser(),pageable);
         }else if (isLoginUserIsAdmin(req) && !isScarceKeyIsEmpty(req)) {
               response =findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullName(req.getSearchBy(), pageable);
         }else  if (isLoginUserIsAdmin(req)&& !isScarceKeyIsEmpty(req)&& !isUploaderEmailIsEmpty(req)) {
             response = findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullNameAndUploader(req.getSearchBy(), req.getUploader(), pageable);
         }else if (isLoginUserIsAdmin(req)&& isScarceKeyIsEmpty(req) && !isUploaderEmailIsEmpty(req)){
             response = reviewService.finAllByUploaderEmail(req.getUploader(),pageable);
         }else if (isLoginUserIsAdmin(req)&& isScarceKeyIsEmpty(req)&& isUploaderEmailIsEmpty(req)){
             response = reviewService.findAll(pageable);
         }
         return response;

   }

    public Response findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullName(String key, Pageable pageable) {
        var page = metaDataRepository.findAllByTitleOrReviewerUsernameOrReviewerEmailOrReviewerFullNameOrderByCreatedAtDesc(key, key, key, key, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));

        return getSuccessResponsePage(HttpStatus.OK,"filter Data",page);
    }
    public Response findAllBySearchKeyWithTitleOrAssigneeEmailOrUserNameOrFullNameAndUploader(String key,String uploaderEmail, Pageable pageable) {
        var page = metaDataRepository.findAllByTitleOrReviewerUsernameOrReviewerEmailOrReviewerFullNameAndUploaderEmailOrderByCreatedAtDesc(key, key, key, key,uploaderEmail, pageable)
                .map(metaData -> modelMapper.map(metaData, VideoMetadataResponseDto.class));

        return getSuccessResponsePage(HttpStatus.OK,"filter Data",page);
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
         return req.getDate() == null;
    }
    private  boolean isActivelyDayIsZero(MetaDataFilterRequest request){
       return request.getLastActivity() == 0;
    }
    private boolean isUploaderEmailIsEmpty(MetaDataFilterRequest req){
         return  req.getUploader().isBlank() || req.getUploader() == null;
    }

}
