package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.model.uploadconfig.UploadConfig;
import com.unriviel.api.service.UploadConfigService;
import com.unriviel.api.util.UrlConstrains;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@APiController
@RequestMapping(UrlConstrains.UploadConfig.ROOT)
public class MediaUploadConfigurationController {
    private final UploadConfigService configService;

    public MediaUploadConfigurationController(UploadConfigService configService) {
        this.configService = configService;
    }
    @PostMapping(UrlConstrains.UploadConfig.CREATE)
    public  ResponseEntity<Object> create(@RequestBody UploadConfig config){
        var response = configService.create(config);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(UrlConstrains.UploadConfig.GET_BY_ID)
    public  ResponseEntity<Object> findById(@PathVariable(required = true) long id){
        var response = configService.findById(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}
