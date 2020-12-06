package com.unriviel.api.dto;

import com.unriviel.api.model.metadata.Audio;
import com.unriviel.api.model.metadata.Images;
import com.unriviel.api.model.metadata.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class VideoMetadataDto {
    private String videoId;
    private String videoName;
    private String videoType;
    private String videoUrl;
    private String title;
    private String description;
    private String videoEncoding;
    private long videoDuration;
    private long videoSize;
    private  int videoFps;
    private int videoResolution;
//    @Type(type = "jsonb")
//    private String images;
    private List<Images> images = new ArrayList<>();
    private String thumbnailUrl;
    private boolean isAdultContent;
    private boolean isIncludePromotion;
    private String recodedYear;
//    @Type(type = "jsonb")
//    private String locations;
    private List<Location> locations = new ArrayList<>();
    @ElementCollection
    private List<String> tags = new ArrayList<>();
    @Embedded
    private Audio audio;
    @ElementCollection
    private List<Integer> contentUses = new ArrayList<>();
    private boolean isApproved;
}
