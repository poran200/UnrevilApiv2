package com.unriviel.api.dto;

import com.unriviel.api.model.FaceBookPages;
import com.unriviel.api.model.InstagramHandles;
import com.unriviel.api.model.WebSiteOrBlogs;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileResponseDto {
    private long id;
    private String profileImageUrl;
    private FaceBookPages faceBookPages;
    private InstagramHandles instagramHandles;
    private WebSiteOrBlogs webSiteOrBlogs;
    private String relevantQsAns_1;
    private String relevantQsAns_2;
    private String relevantQsAns_3;
    private  int totalUploadContent;
    private  int totalApproved;
}
