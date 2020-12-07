package com.unriviel.api.service;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataDto;
import org.springframework.data.domain.Pageable;

public interface VideoMetaDataService {
    Response save(VideoMetadataDto metadataDto);
    Response findByVideoId(String videoId);
    Response finByUserEmail(String email, Pageable pageable);
    Response approvedVideo(String videoId,boolean isApproved,String reviewedBy);
}
