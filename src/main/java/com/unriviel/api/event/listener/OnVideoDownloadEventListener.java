package com.unriviel.api.event.listener;

import com.unriviel.api.event.OnVideoDawonLoadEvent;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.service.impl.DownloadVideoStorageService;
import com.unriviel.api.util.UrlConstrains;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@Log4j2
public class OnVideoDownloadEventListener   implements ApplicationListener<OnVideoDawonLoadEvent> {
 private  final DownloadVideoStorageService videoStorageService;
 private final VideoMetaDataService  metaDataService;

    @Autowired
    public OnVideoDownloadEventListener(DownloadVideoStorageService videoStorageService, VideoMetaDataService metaDataService) {
        this.videoStorageService = videoStorageService;
        this.metaDataService = metaDataService;
    }

    @Override
    @Async
    public void onApplicationEvent(OnVideoDawonLoadEvent event) {
         this.saveData(event);
    }

    public void saveData(OnVideoDawonLoadEvent event){
        var path = ServletUriComponentsBuilder.fromRequest(event.getRequest())
                .replacePath(UrlConstrains.VideoUpload.ROOT).path("/");
        videoStorageService.storeFile(event.getVideoUrl(),event.getVideoId());
        var videoInfo = videoStorageService.extractMetadata(event.getVideoId(), event.getVideoId());
        if (videoInfo.isPresent()){
            videoInfo.get().setVideoUrl(path.path("videos/"+event.getVideoId()).toUriString());
            log.info("Url = "+videoInfo.get().getVideoUrl());
            metaDataService.saveVideoInfo(event.getVideoId(),videoInfo.get());
        }
    }
}
