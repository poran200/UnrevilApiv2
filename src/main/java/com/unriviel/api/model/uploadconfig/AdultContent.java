package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Data@Embeddable
public class AdultContent {
    private String contentTitle;
    private  String contentParagraph;
    private  String  contentOption1;
    private String contentOption2;
}
