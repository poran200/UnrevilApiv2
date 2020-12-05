package com.unriviel.api.model.uploadconfig;

import lombok.Data;

import javax.persistence.Embeddable;

@Data@Embeddable
public class Usages {
    private String  option1;
    private String  option2;
    private String  option3;
    private String  option4;
    private String  option5;
}
