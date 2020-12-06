package com.unriviel.api.util;

import com.unriviel.api.dto.ProfileDto;
import com.unriviel.api.model.Profile;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public  class  DtoToModel {
    @Autowired
    ModelMapper modelMapper;

    public DtoToModel() { }

    public  Profile createProfile (ProfileDto dto){
        return modelMapper.map(dto, Profile.class);
    }

//    public VideoMetaData createFromDto(VideoMetadataDto dto) {
//        var videoMetaData = modelMapper.map(dto, VideoMetaData.class);
////        if (dto.getImages() != null){
////            var imageJson = objectToJSonString(dto.getImages());
////            videoMetaData.setImages(imageJson);
////        }
////        if (dto.getLocations() != null){
////            var jSonString = objectToJSonString(dto.getLocations());
////            videoMetaData.setLocations(jSonString);
////        }
//        return videoMetaData;
//    }
//
//    public String objectToJSonString(Object dto){
//        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        try {
//            return mapper.writeValueAsString(dto);
//        } catch (JsonProcessingException e) {
//            log.info("json mapping exception :- "+e.getMessage());
//            e.printStackTrace();
//            return "";
//        }
//    }
}
