package com.unriviel.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReUploadMeataData {
    private String videoId;
    private String videoName;
    private String videoEncoding;
    private long videoDuration;
    private long videoSize;
    private  int videoFps;
    private  int videoResolution;
}
