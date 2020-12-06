package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.util.ContentType;
import com.unriviel.api.util.UrlConstrains;
import com.unriviel.api.util.VideoIdGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@APiController
@RequestMapping(UrlConstrains.VideoIdGenerate.ROOT)
public class VideoIdGenerateController {

    @GetMapping(UrlConstrains.VideoIdGenerate.GENERATE)
    public ResponseEntity<Object> generateId(@PathVariable(required = true) int numberOfId){

          Map<Integer, String> videosId  = new HashMap<>();
          for (int i = 1; i<= numberOfId; i++){
              var uuid = VideoIdGenerator.generateUUID(ContentType.VIDEO, 1000);
              videosId.put(i,uuid);
          }
        return ResponseEntity.ok().body(videosId);
    }
}
