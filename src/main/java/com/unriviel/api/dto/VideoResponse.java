package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoResponse {
    private String videoId;
    private String  url;
    private boolean isUploaded;
    private String userEmail;
}
