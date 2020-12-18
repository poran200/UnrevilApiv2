package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class InfluencerResponseDto {
    private String userName;
    private String email;
    private String fullName;
    private String createdAt;
    private boolean isAccountNoneLocked;
    private int  totalUpload;
    private  int totalApproved;
    private  String profileLink;

}
