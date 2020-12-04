package com.unriviel.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class WebSiteOrBlogs {
     private  String site1;
     private  String site2;
}
