package com.unriviel.api.controller.upload;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.VideoMetadataRequestDto;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.UrlConstrains;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@APiController
@RequestMapping(UrlConstrains.VideoMetaDataManagement.ROOT)
public class VideoMetadataController {
    private final VideoMetaDataService videoMetaDataService;

    public VideoMetadataController(VideoMetaDataService videoMetaDataService) {
        this.videoMetaDataService = videoMetaDataService;
    }
    @GetMapping(UrlConstrains.VideoMetaDataManagement.GET_BY_ID)
    public ResponseEntity getById(@PathVariable String id){
        var metadata = videoMetaDataService.findByVideoId(id);
        return ResponseEntity.status((int) metadata.getStatusCode()).body(metadata);
    }

//    @PostMapping(UrlConstrains.VideoMetaDataManagement.CREATE)
//    public ResponseEntity<Object> create(@RequestBody(required = true) VideoMetadataRequestDto dto ){
//        var response = videoMetaDataService.save(dto);
//        return ResponseEntity.status((int) response.getStatusCode()).body(response);
//    }
    @PutMapping(UrlConstrains.VideoMetaDataManagement.UPDATE)
    public ResponseEntity<Object> update(@RequestBody(required = true)  VideoMetadataRequestDto dto,
                                         @PathVariable(required = true) String videoId
                                         ){
        var response = videoMetaDataService.update(videoId,dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(UrlConstrains.VideoMetaDataManagement.GET_BY_EMAIL)
    public ResponseEntity<Object> findByEmail(@PathVariable String email,
                                              @RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "20") int pageSize){
        var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending());
        var response = videoMetaDataService.finByUserEmail(email, pageRequest);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

