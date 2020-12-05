package com.unriviel.api.model.uploadconfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode
public class UploadConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    @Embedded
    private MediaGuideline guideline;
    private  String videoTitle;
    private  String videoDescription;
    @Embedded
    private  Images images;
    @Embedded
    private  Thumbnail thumbnail;
    @Embedded
    private  AdultContent adultContent;
    @Embedded
    private  Promotion promotion;
    @Embedded
    private Recorded recorded;
    @Embedded
    private  Location location ;
    @Embedded
    private  Tag tag;
    @Embedded
    private Usages usages;

}
