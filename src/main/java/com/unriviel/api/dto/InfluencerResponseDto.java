package com.unriviel.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unriviel.api.model.FaceBookPages;
import com.unriviel.api.model.InstagramHandles;
import com.unriviel.api.model.User;
import com.unriviel.api.model.WebSiteOrBlogs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class InfluencerResponseDto {
    private long id;
    private String profileImageUrl;
    private FaceBookPages faceBookPages;
    private InstagramHandles instagramHandles;
    private WebSiteOrBlogs webSiteOrBlogs;
    private String relevantQsAns_1;
    private String relevantQsAns_2;
    private String relevantQsAns_3;
    @JsonIgnoreProperties(value = {"password","active","roles","isEmailVerified",
            "totalAssigned","totalReviewed","createdAt"})
    private User user;

}
