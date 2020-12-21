package com.unriviel.api.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

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
     private HttpServletRequest request;

    public OnVideoDamonLoadEvent(String videoUrl, String videoId, String userEmail, HttpServletRequest request) {
        super(videoUrl);
        this.videoUrl = videoUrl;
        this.videoId = videoId;
        this.userEmail = userEmail;
        this.request = request;
    }

    public OnVideoDamonLoadEvent(String videoUrl, String videoId) {
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

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
