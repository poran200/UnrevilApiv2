package com.unriviel.api.model;

import com.unriviel.api.dto.VideoMetadataRequestDto;
import com.unriviel.api.model.metadata.Audio;
import com.unriviel.api.model.metadata.Coordinate;
import com.unriviel.api.model.metadata.Images;
import com.unriviel.api.model.metadata.Location;
import com.unriviel.api.util.DtoToModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class JsonTest {

    @Autowired
    DtoToModel dtoToModel;
    VideoMetadataRequestDto dto = new VideoMetadataRequestDto();

    @Test
   public void test(){
      dto.setVideoId("video-1");
      dto.setVideoName("name");
      dto.setVideoType("mp4");
      dto.setVideoUrl("http://");
      dto.setDescription("description");
        var images = new Images();
        images.setUrl("imageUrl");
         List<Images> images1 = new ArrayList<>();
         List<Coordinate> coordinates = new ArrayList<>();
         coordinates.add(new Coordinate(2,3));
//         images.setCoordinates(coordinates);
         images1.add(images);
        dto.setImages(images1);

        dto.setAdultContent(false);
        dto.setIncludePromotion(false);
        dto.setRecodedYear("2005");
        Location location = new Location();
        location.setName("locationName");
//        location.setLag(233345);
//        location.setLag(32221133);
        var locations = new ArrayList<Location>();
        locations.add(location);
        dto.setLocations((locations));
        dto.setTags(new ArrayList<>(Collections.singleton("tags")));
        dto.setAudio(new Audio(true,false,"123322"));
        dto.setContentUses(List.of(1,2,3));
        dto.setApproved(false);

    }
}
