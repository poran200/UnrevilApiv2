package com.unriviel.api.controller.upload;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.VideoMetadataDto;
import com.unriviel.api.model.metadata.*;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.DtoToModel;
import com.unriviel.api.util.UrlConstrains;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@APiController
@RequestMapping(UrlConstrains.VideoMetaDataManagement.ROOT)
public class VideoMetadataController {
    private final VideoMetaDataService videoMetaDataService;
    private final DtoToModel dtoToModel;
    @Autowired
    private  ModelMapper modelMapper;
    public VideoMetadataController(VideoMetaDataService videoMetaDataService, DtoToModel dtoToModel) {
        this.videoMetaDataService = videoMetaDataService;
        this.dtoToModel = dtoToModel;
    }
    @GetMapping("/")
    public VideoMetadataDto getById(){
        VideoMetadataDto dto = new VideoMetadataDto();
        dto.setVideoId("video-1");
        dto.setVideoName("name");
        dto.setVideoType("mp4");
        dto.setVideoUrl("http://");
        dto.setDescription("description");
        var images = new Images();
        images.setUrl("imageUrl");
        List<Images> images1 = new ArrayList<>();

        Coordinate coordinate1 = new Coordinate(2,3);
        Coordinate coordinate2 = new Coordinate(2,3);
        Coordinate coordinate3 = new Coordinate(2,3);
        Coordinate coordinate4 = new Coordinate(2,3);
        images.setFirst(coordinate1);
        images.setSecond(coordinate2);
        images.setThird(coordinate3);
        images.setFourth(coordinate4);
        images1.add(images);
        dto.setImages(images1);

        dto.setAdultContent(false);
        dto.setIncludePromotion(false);
        dto.setRecodedYear("2005");
        Location location = new Location();
        LandMark landMark = new LandMark("name",2,3);
        location.setLandMarks(List.of(landMark));
        location.setName("locationName");
        var locations = new ArrayList<Location>();
        locations.add(location);
        dto.setLocations((locations));
        dto.setTags(new ArrayList<>(Collections.singleton("tags")));
        dto.setAudio(new Audio(true,false,"123322"));
        dto.setContentUses(List.of(1,2,3));
        dto.setApproved(false);
        return dto;
    }

    @PostMapping(UrlConstrains.VideoMetaDataManagement.CREATE)
    public ResponseEntity<Object> create(@RequestBody(required = true) VideoMetadataDto dto ){
         return null;
    }
}
