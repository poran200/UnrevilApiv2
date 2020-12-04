package com.unriviel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id"})
public class Profile   implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String profileImageUrl;
    @Embedded
    private FaceBookPages faceBookPages;
    @Embedded
    private InstagramHandles instagramHandles;
    @Embedded
    private WebSiteOrBlogs webSiteOrBlogs;
    private String relevantQsAns_1;
    private String relevantQsAns_2;
    private String relevantQsAns_3;
    private  int totalUploadContent;
    private  int totalApproved;
    @OneToOne
    @JsonIgnore
    private User user;





}
