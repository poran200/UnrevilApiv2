package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Data@Embeddable
public class Thumbnail {
    private String thumbnailTitle;
    private String thumbnailStatus;
}
