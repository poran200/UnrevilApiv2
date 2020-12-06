package com.unriviel.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoMetadataDto;
import com.unriviel.api.model.metadata.VideoMetaData;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.VideoMetaDataService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
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
    public VideoMetaData createFromDto(VideoMetadataDto dto) {
//        var videoMetaData = modelMapper.map(dto, VideoMetaData.class);
//        if (dto.getImages() != null){
//            var imageJson = objectToJSonString(dto.getImages());
//            videoMetaData.setImages(imageJson);
//        }
//        if (dto.getLocations() != null){
//            var jSonString = objectToJSonString(dto.getLocations());
//            videoMetaData.setLocations(jSonString);
//        }
        return null;
    }

    public String objectToJSonString(Object dto){
         ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.info("json mapping exception :- "+e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public Response save(VideoMetaData metaData) {
        return null;
    }

    @Override
    public Response findByVideoId(String videoId) {
        return null;
    }

    @Override
    public Response finByUserEmail(String email, Pageable pageable) {
        return null;
    }

    @Override
    public Response approvedVideo(String videoId, boolean isApproved, String reviewedBy) {
        return null;
    }
}
