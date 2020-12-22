package com.unriviel.api.controller.upload;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.service.impl.DownloadVideoStorageService;
import com.unriviel.api.service.impl.UserService;
import com.unriviel.api.service.impl.VideoStorageService;
import com.unriviel.api.util.ResponseBuilder;
import com.unriviel.api.util.UrlConstrains;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Log4j2
@APiController
@RequestMapping(UrlConstrains.VideoUpload.ROOT)
public class VideoUploadController   {
    private final VideoStorageService videoStorageService;
    private final VideoMetaDataService metaDataService;
    private final DownloadVideoStorageService downloadVideoStorageService;
    private final UserService userService;
    public VideoUploadController(VideoStorageService videoStorageService, VideoMetaDataService metaDataService, DownloadVideoStorageService downloadVideoStorageService, UserService userService) {
        this.videoStorageService = videoStorageService;
        this.metaDataService = metaDataService;
        this.downloadVideoStorageService = downloadVideoStorageService;
        this.userService = userService;
    }

    @PostMapping(UrlConstrains.VideoUpload.CREATE)
    public ResponseEntity<Object> upload(@RequestBody(required = true)MultipartFile file,
                                         @PathVariable String videoId,
                                         @PathVariable String userEmail,
                                         HttpServletRequest request) {
        if (videoId == null || userEmail == null || file.isEmpty()){
            ResponseEntity.badRequest().body("user email and videoId file is  require");
        }
        var existsByEmail = userService.existsByEmail(userEmail);
        if (!existsByEmail){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body( ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                    "User not found [Email]="+userEmail));
        }else if(!metaDataService.isExistById(videoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body( ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                    "Video id  not found" +"make sure you create first"+ " [VideoID]="+videoId));
        }else {
            var response = videoStorageService.storeFile(file, videoId, request, userEmail);
            return (ResponseEntity.status((int) response.getStatusCode()).body(response));
        }
    }
    @Async
    @PostMapping(UrlConstrains.VideoUpload.REUPLOAD)
    public CompletableFuture<ResponseEntity<Object>> reUpload(@RequestBody(required = true)MultipartFile file,
                                                              @PathVariable(required = true) String videoId,
                                                              @PathVariable(required = true) String userEmail,
                                                              HttpServletRequest request) throws RuntimeException {
        if (videoId == null || userEmail == null || file.isEmpty()){
            ResponseEntity.badRequest().body("user email and videoId require");
        }
            var response = videoStorageService.reStore(file,videoId,userEmail,request);
            return CompletableFuture.completedFuture(ResponseEntity.status((int) response.getStatusCode()).body(response));

    }
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = videoStorageService.loadFileAsResource(fileName);
        byte[] bytes = resource.getInputStream().readAllBytes();
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getName());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

//        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

//        assert contentType != null;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new ByteArrayResource(bytes));
    }

    @GetMapping("videos/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = downloadVideoStorageService.loadFileAsResource(fileName);
        byte[] bytes = resource.getInputStream().readAllBytes();
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getName()+".mp4");
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

//        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

//        assert contentType != null;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new ByteArrayResource(bytes));
    }

}
