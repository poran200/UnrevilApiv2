package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class ImagesLable {
    private String imageTitle;
    private String imageParagraph;
}
