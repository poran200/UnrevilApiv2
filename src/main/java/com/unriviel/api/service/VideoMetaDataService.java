package com.unriviel.api.service;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoExternalUrlRequest;
import com.unriviel.api.dto.VideoMetadataRequestDto;
import com.unriviel.api.dto.VideoResponse;
import org.springframework.data.domain.Pageable;

public interface VideoMetaDataService {
    Response save(VideoMetadataRequestDto metadataDto);
    Response findByVideoId(String videoId);
    Response findByVideoIdReview(String videoId);
    Response finByUserEmail(String email, Pageable pageable);
    Response approvedVideo(String videoId,boolean isApproved,String reviewedBy);
    void saveVideoStatus(VideoResponse response);
    boolean isExistById(String videoId);
    Response update(String videoId, VideoMetadataRequestDto videoMetadataRequestDto);
    Response saveWithExternalUrl(VideoExternalUrlRequest request);
}
