package com.unriviel.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Embeddable
@NoArgsConstructor
@Data
public class FaceBookPages {
    private String  page1;
    private  String page2;
}
