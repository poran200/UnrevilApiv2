package com.unriviel.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class ReviewerResponseDto {
    private String userName;
    private String email;
    private String fullName;
    private boolean isAccountNoneLocked;
    private  int totalAssigned;
    private int totalReviewed;

}
