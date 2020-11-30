package com.unriviel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode()
public class Profile   implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String profileImageUrl;
    @ElementCollection
    private List<String> socialMediaLinks;
    @OneToMany(mappedBy = "profile",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"profile"})
    private List<RelevantQsAns> relevantQsAnsList;
    private  int totalUploadContent;
    private  int totalApproved;
    @OneToOne
    private User user;

    public void addSocialMediaLinks(List<String> urls){
        if (socialMediaLinks == null){
            socialMediaLinks = new ArrayList<>();
        }
        socialMediaLinks.addAll(urls);
    }
    public void addQuestionAns(RelevantQsAns ans){
         if (relevantQsAnsList == null){
             this.relevantQsAnsList = new ArrayList<>();
         }
         ans.setProfile(this);
         relevantQsAnsList.add(ans);
    }

}
