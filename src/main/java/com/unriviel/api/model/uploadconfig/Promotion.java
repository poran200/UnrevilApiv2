package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Data@Embeddable
public class Promotion {
    private String promoTitle;
    private String promoParagraph;
    private String optionTitle;

}
