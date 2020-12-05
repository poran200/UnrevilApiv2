package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Data@Embeddable
public class Recorded {
    private  String recordedTitle;
    private  String recordedParagraph;
    private  String dates;
}
