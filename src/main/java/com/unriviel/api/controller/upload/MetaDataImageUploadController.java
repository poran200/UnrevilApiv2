package com.unriviel.api.controller.upload;


import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.Response;
import com.unriviel.api.service.impl.FileStorageService;
import com.unriviel.api.util.UrlConstrains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@APiController
@RequestMapping(UrlConstrains.MetadataImageUpload.ROOT)
public class MetaDataImageUploadController {

    private static final Logger logger = LoggerFactory.getLogger(MetaDataImageUploadController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {


        Response response = fileStorageService.storeFile(file);
        if (response.getStatusCode() == 201) {
            var fileName =(String) response.getContent();
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(UrlConstrains.MetadataImageUpload.ROOT+"/metadata/")
                    .path(fileName)
                    .toUriString();
            return ResponseEntity.ok().body(fileDownloadUri);
        }
       return ResponseEntity.status((int) response.getStatusCode()).body(response);

    }

    @PostMapping("/uploadMultipleFiles")
    public List<ResponseEntity> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/metadata/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        byte[] bytes = resource.getInputStream().readAllBytes();
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getName());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
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
