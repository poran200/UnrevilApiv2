package com.unriviel.api.controller.upload;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.service.impl.VideoStorageService;
import com.unriviel.api.util.UrlConstrains;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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
public class VideoUploadController {
    private final VideoStorageService videoStorageService;

    public VideoUploadController(VideoStorageService videoStorageService) {
        this.videoStorageService = videoStorageService;
    }
    @Async
    @PostMapping(UrlConstrains.VideoUpload.CREATE)
    public CompletableFuture<ResponseEntity<Object>> upload(@RequestBody(required = true)MultipartFile file,HttpServletRequest request){
        var response = videoStorageService.storeFile(file,request);
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


}
