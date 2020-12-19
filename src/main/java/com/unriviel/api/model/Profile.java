package com.unriviel.api.model;

import com.unriviel.api.model.audit.DateAudit;
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
@EqualsAndHashCode(of = {"id"}, callSuper = false)
//@JsonIgnoreProperties(value = {"createdAt","updatedAt"})
public class Profile  extends DateAudit implements Serializable {

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
    @OneToOne
//    @JsonIgnore
    private User user;





}
