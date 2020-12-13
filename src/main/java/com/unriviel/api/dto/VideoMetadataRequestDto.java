package com.unriviel.api.dto;

import com.unriviel.api.model.metadata.Audio;
import com.unriviel.api.model.metadata.Images;
import com.unriviel.api.model.metadata.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VideoMetadataRequestDto {
    @NotEmpty(message = "Video id can not  empty")
    private String videoId;
    private String videoName;
    private String videoType;
    private String videoUrl;
    private String videoEncoding;
    private long videoDuration;
    private long videoSize;
    private  int videoFps;
    private int videoResolution;

    private String title;
    private String description;
//    @Size(min=1, max=3,message = "Minimum 1 image attach on video and Max 3")
    private List<Images> images;
    private String thumbnailUrl;
    private boolean isAdultContent;
    private boolean isIncludePromotion;
    private String recodedYear;

    List<Location> locations;

    private List<String> tags = new ArrayList<>();

    private Audio audio;

    private List<Integer> contentUses = new ArrayList<>();
    private boolean isApproved;

}
