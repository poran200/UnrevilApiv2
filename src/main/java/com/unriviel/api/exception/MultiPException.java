package com.unriviel.api.exception;

import com.unriviel.api.dto.VideoResponse;

public class MultiPException extends org.springframework.web.multipart.MultipartException {

    public MultiPException(VideoResponse msg) {
        super(msg.getVideoId() + "," + msg.getUrl() + "," + msg.getUserEmail());
    }

}
