package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class InfluencerResponseDto {
    private String userName;
    private String email;
    private String fullName;
    private boolean isAccountNoneLocked;
    private int  totalUpload;
    private  int totalApproved;
    private  String profileLink;

}
