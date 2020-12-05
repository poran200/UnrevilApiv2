package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable@Data
public class Location {
    private String locationTitle;
    private String locationParagraph;
}
