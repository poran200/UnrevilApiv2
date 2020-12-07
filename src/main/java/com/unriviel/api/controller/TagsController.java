package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.repository.VideoMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@APiController
@RequestMapping("api/v1/tags")
public class TagsController {
    @Autowired
    VideoMetaDataRepository videoMetaDataRepository;

    @GetMapping("/{tag}")
    public ResponseEntity<Object> tagsList(@PathVariable String tag){
////        var request = PageRequest.of(0, 10);
//        List<String> allTag = videoMetaDataRepository.allTag();
//        var list = allTag.stream().filter(s -> s.startsWith(tag)).limit(10).collect(Collectors.toList());
//        return ResponseEntity.ok().body(list);
        return null;
    }
}
