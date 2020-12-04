package com.unriviel.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class InstagramHandles {
     private String   handle1;
     private  String  handle2;
}
