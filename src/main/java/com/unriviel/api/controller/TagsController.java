package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.model.metadata.VideoMetaData;
import com.unriviel.api.repository.VideoMetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@APiController
@RequestMapping("api/v1/tags")
public class TagsController {
    @Autowired
    VideoMetaDataRepository videoMetaDataRepository;

    @GetMapping("/{tag}")
    public ResponseEntity<Object> tagsList(@PathVariable String tag){
        var collect = videoMetaDataRepository.findAll().stream().map(VideoMetaData::getTags).collect(Collectors.toList());
        List<String> tagList = new ArrayList<>();
        for (List<String> list : collect){
            tagList.addAll(list.stream()
                    .filter(s -> s.startsWith(tag))
                    .collect(Collectors.toList()));
        }
        var finalList = tagList.stream()
                .filter(s -> s.startsWith(tag))
                .limit(10)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(finalList);
    }
}
