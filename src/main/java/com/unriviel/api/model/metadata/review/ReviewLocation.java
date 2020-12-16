package com.unriviel.api.model.metadata.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReviewLocation implements Serializable {
    private String locationName;
    private AnsStatus isContainVideoInfo;


}
