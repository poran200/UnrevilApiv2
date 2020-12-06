package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Data@Embeddable
public class TagLabel {
    private String tagTitle;
    private  String tagParagraph;
}
