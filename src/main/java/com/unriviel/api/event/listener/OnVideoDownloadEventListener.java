package com.unriviel.api.event.listener;

import com.unriviel.api.event.OnVideoDamonLoadEvent;
import com.unriviel.api.service.impl.DownloadVideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnVideoDownloadEventListener   implements ApplicationListener<OnVideoDamonLoadEvent> {
 private  final DownloadVideoStorageService videoStorageService;

    @Autowired
    public OnVideoDownloadEventListener(DownloadVideoStorageService videoStorageService) {
        this.videoStorageService = videoStorageService;
    }

    @Override
    public void onApplicationEvent(OnVideoDamonLoadEvent event) {

    }
}
