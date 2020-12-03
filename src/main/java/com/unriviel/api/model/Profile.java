package com.unriviel.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<String> socialMediaLinks;
    @OneToMany(mappedBy = "profile",fetch = FetchType.LAZY,cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonIgnoreProperties({"profile"})
    private List<RelevantQsAns> relevantQsAnsList;
    private  int totalUploadContent;
    private  int totalApproved;
    @OneToOne
    @JsonIgnore
    private User user;

    public void addSocialMediaLinks(Set<String> urls){
        if (this.socialMediaLinks == null)
            this.socialMediaLinks = new HashSet<>();
       for (String s: urls){
           boolean add = socialMediaLinks.add(s);
       }
    }
    public void addQuestionAns(RelevantQsAns ans){
         if (relevantQsAnsList == null){
             this.relevantQsAnsList = new ArrayList<>();
         }
         ans.setProfile(this);
         relevantQsAnsList.add(ans);
    }

}
