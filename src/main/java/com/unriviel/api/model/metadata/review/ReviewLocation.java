package com.unriviel.api.model.metadata.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewLocation {
    private String locationName;
    private AnsStatus isContainVideoInfo;


}
