package com.unriviel.api.event;

import org.springframework.context.ApplicationEvent;

public class OnVideoDamonLoadEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
     private final String videoUrl;
     private final String videoId;
     private String userEmail;
    public OnVideoDamonLoadEvent( String videoUrl,String videoId) {
        super(videoUrl);
        this.videoId = videoId;
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
