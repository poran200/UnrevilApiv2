package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.VideoResponse;
import com.unriviel.api.model.CustomUserDetails;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.ContentType;
import com.unriviel.api.util.UrlConstrains;
import com.unriviel.api.util.VideoIdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@APiController
@RequestMapping(UrlConstrains.VideoIdGenerate.ROOT)
public class VideoIdGenerateController {
  private  final VideoMetaDataService videoMetaDataService;

    public VideoIdGenerateController(VideoMetaDataService videoMetaDataService) {
        this.videoMetaDataService = videoMetaDataService;
    }

    @GetMapping(UrlConstrains.VideoIdGenerate.GENERATE)
    public ResponseEntity<Object> generateId(@PathVariable(required = true) int numberOfId){
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         CustomUserDetails userDetails = (CustomUserDetails) principal;
        var email = userDetails.getEmail();

        Map<Integer, String> videosId  = new HashMap<>();
          for (int i = 1; i<= numberOfId; i++){
              Random random = new Random();
              // generate a random integer from 0 to 899, then add 100
              int x = random.nextInt(900) + 100;
              var uuid = VideoIdGenerator.generateUUID(ContentType.VIDEO, x);
              videoMetaDataService.saveVideoStatus(new VideoResponse(uuid,null,false,email));
              videosId.put(i,uuid);
          }
        return ResponseEntity.ok().body(videosId);
    }
}
