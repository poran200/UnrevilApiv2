package com.unriviel.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.User;
import com.unriviel.api.model.metadata.Audio;
import com.unriviel.api.model.metadata.Images;
import com.unriviel.api.model.metadata.Location;
import com.unriviel.api.model.metadata.review.ReviewQsAns;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class VideoMetadataResponseDto {
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
    private List<Images> images;

    private String thumbnailUrl;
    private String cropThumbnailUrl;
    private boolean isAdultContent;
    private boolean isIncludePromotion;
    private String recodedYear;

    List<Location> locations;
    private List<String> tags;
    private Audio audio;
    private List<Integer> contentUses;
    private ReviewStatus reviewStatus;
    private boolean isUploaded;
    private ReviewStatus reviewProcess;
    private boolean isAssigned;
    private  java.util.Date approvedAt;
    private  java.util.Date assignedAt;
    private ReviewQsAns reviewQsAns;
    @JsonIgnoreProperties(value = {"password","active","roles","isEmailVerified","totalUpload","totalApproved",
            "totalAssigned","totalReviewed","createdAt","updatedAt"})
    private User uploader;
    @JsonIgnoreProperties(value = {"password","active","roles","isEmailVerified","totalUpload","totalApproved",
            "totalAssigned","totalReviewed","updatedAt","createdAt"})
    private User reviewer;
}
