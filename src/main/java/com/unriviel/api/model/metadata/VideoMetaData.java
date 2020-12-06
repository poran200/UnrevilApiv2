package com.unriviel.api.model.metadata;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "videoId")
@Entity
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class VideoMetaData  implements Serializable {
     static final long serialVersionUID = 1L;
     @Id
     private String videoId;
     private String videoName;
     private String videoType;
     private String videoUrl;
     private String videoEncoding;
     private long videoDuration;
     private long videoSize;
     private  int videoFps;
     private int videoResolution;

     private String title;
     @Column(length = 1000)
     private String description;
     @Type(type = "json")
     @Column(columnDefinition = "json")
     private List<Images> images;

     private String thumbnailUrl;
     private boolean isAdultContent;
     private boolean isIncludePromotion;
     private String recodedYear;

//     private String locations;
     @Type(type = "json")
     @Column(columnDefinition = "json")
     List<Location> locations;
     @ElementCollection
     private List<String> tags = new ArrayList<>();
     @Embedded
     private Audio audio;
     @ElementCollection
     private List<Integer> contentUses = new ArrayList<>();
     private boolean isApproved;
     private String userEmail;
}
