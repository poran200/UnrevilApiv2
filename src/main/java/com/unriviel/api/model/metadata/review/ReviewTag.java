package com.unriviel.api.model.metadata.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewTag {
    private String tagName;
    private AnsStatus isRelated;

}
