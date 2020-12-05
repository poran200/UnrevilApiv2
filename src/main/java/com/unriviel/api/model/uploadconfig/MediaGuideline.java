package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class MediaGuideline {
    private String guidelineTitle;
    private String fps;
    private String encoding;
    private String ratio;
}
