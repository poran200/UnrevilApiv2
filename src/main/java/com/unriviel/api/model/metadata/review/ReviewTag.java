package com.unriviel.api.model.metadata.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ReviewTag implements Serializable {
    private String tagName;
    private AnsStatus isRelated;

}
