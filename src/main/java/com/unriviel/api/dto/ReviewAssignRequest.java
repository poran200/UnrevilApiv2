package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data@AllArgsConstructor
public class ReviewAssignRequest {
    @NotEmpty(message = "videoId can not empty")
    private String videoId;
    @NotEmpty(message = "reviewerEmail can not empty")
    private String reviewerEmail;
}
