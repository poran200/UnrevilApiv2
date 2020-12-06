package com.unriviel.api.service;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataDto;
import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.domain.Pageable;

public interface VideoMetaDataService {
    public VideoMetaData createFromDto(VideoMetadataDto dto);
    Response save(VideoMetaData metaData);
    Response findByVideoId(String videoId);
    Response finByUserEmail(String email, Pageable pageable);
    Response approvedVideo(String videoId,boolean isApproved,String reviewedBy);
}
