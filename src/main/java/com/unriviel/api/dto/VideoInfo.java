package com.unriviel.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoInfo {
    public static final String URL_INVALID = "Url is not valid or not  public assessable";
    public static  final String VIDEO_FPS_NOT_GOOD= "Video fps not Valid";
    public static  final  String ASPECT_RATIO_NOT_VALID="Aspect ratio not valid";
    public static final String VIDEO_TYPE_NOT_VALID= "Video encoding not valid";
    public static final String VIDEO_SAVED="Video successfully saved";
    private String videoId;
    private String videoName;
    private String videoType;
    private String videoUrl;
    private String externalVideoUrl;
    private String videoEncoding;
    private long videoDuration;
    private long videoSize;
    private  int videoFps;
    private int videoResolution;
    private int height;
    private int width;
    private String statusMassage;
    private boolean isValid;

//    Video will be validated for 24-30 FPS, mimetype(mp4,mov,avi), aspect ratio (9:16)

}
