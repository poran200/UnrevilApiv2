package com.unriviel.api.controller.upload;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.ReUploadMeataData;
import com.unriviel.api.dto.VideoExternalUrlRequest;
import com.unriviel.api.dto.VideoMetadataRequestDto;
import com.unriviel.api.event.OnVideoDawonLoadEvent;
import com.unriviel.api.model.CustomUserDetails;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.UrlConstrains;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@APiController
@RequestMapping(UrlConstrains.VideoMetaDataManagement.ROOT)
public class VideoMetadataController {
    private final VideoMetaDataService videoMetaDataService;
    private final ApplicationEventPublisher eventPublisher;
    public VideoMetadataController(VideoMetaDataService videoMetaDataService, ApplicationEventPublisher eventPublisher) {
        this.videoMetaDataService = videoMetaDataService;
        this.eventPublisher = eventPublisher;
    }
    @GetMapping(UrlConstrains.VideoMetaDataManagement.GET_BY_ID)
    public ResponseEntity getById(@PathVariable String id){
        var metadata = videoMetaDataService.findByVideoId(id);
        return ResponseEntity.status((int) metadata.getStatusCode()).body(metadata);
    }
    @GetMapping(UrlConstrains.VideoMetaDataManagement.GET_BY_ID_REVIEW)
    public ResponseEntity getByIdReview(@PathVariable String id){
        var metadata = videoMetaDataService.findByVideoIdReview(id);
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
    @PutMapping(UrlConstrains.VideoMetaDataManagement.RE_UPDATE)
    public ResponseEntity<Object> updateVideoMetaData(@RequestBody(required = true) ReUploadMeataData dto,
                                         @PathVariable(required = true) String videoId
    ){
        var response = videoMetaDataService.videoMetaDataReUpdate(videoId,dto);
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
    @PostMapping(UrlConstrains.VideoMetaDataManagement.CREATE)
    public ResponseEntity createVideoExternalUrl(@Valid @RequestBody VideoExternalUrlRequest request, HttpServletRequest req){
       var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if (principal instanceof UserDetails){
            var details = (CustomUserDetails) principal;
           var username = details.getEmail();
           System.out.println("username = " + username);
           var response = videoMetaDataService.saveWithExternalUrl(request,username);
           OnVideoDawonLoadEvent event =new OnVideoDawonLoadEvent(request.getUrl(),request.getVideoId(),username,req);
           eventPublisher.publishEvent(event);
           return ResponseEntity.status((int) response.getStatusCode()).body(response);
       }
       return ResponseEntity.badRequest().build();
    }
}

