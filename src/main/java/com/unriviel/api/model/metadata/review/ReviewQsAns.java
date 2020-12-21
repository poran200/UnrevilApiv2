package com.unriviel.api.model.metadata.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.unriviel.api.model.metadata.review.AnsStatus.YES;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@TypeDef(name = "json",typeClass = JsonStringType.class)
public class ReviewQsAns  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(value = EnumType.STRING)
    private AnsStatus isTitleCorrect;
    @Enumerated(value = EnumType.STRING)
    private AnsStatus isDescriptionSuitable;
    @Enumerated(value = EnumType.STRING)
    private AnsStatus isAdultContent;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    List<ReviewImage> reviewImages = new ArrayList<>();
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private ReviewImage thumbnailImage;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    List<ReviewTag> reviewTags = new ArrayList<>();
    @Type(type = "json")
    @Column(columnDefinition = "json")
    List<ReviewLocation> reviewLocations = new ArrayList<>() ;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    List<Integer> audioOptionList = new ArrayList<>();
    String comment;

    @JsonIgnore
    public boolean isApproved(){
        boolean isApproved = false ;
        if (!this.onReviewOnProcessIsRunning()){
            if (this.isTitleCorrect != null && this.isDescriptionSuitable != null){
                isApproved = this.isTitleCorrect.equals(YES) && this.isDescriptionSuitable.equals(YES);
            }

            if (reviewImages != null && isApproved){
                for (ReviewImage image : reviewImages) {
                    if (!image.isOk()){
                        isApproved = false;
                        break;
                    }
                    isApproved = true;
                }

            } if (reviewLocations != null && isApproved){
                for (ReviewLocation location : reviewLocations) {
                    if (!location.getIsContainVideoInfo().equals(YES)) {
                        isApproved = false;
                        break;
                    }
                    isApproved = true;
                }
            } if (reviewTags !=null && isApproved){
                for (ReviewTag tag : reviewTags) {
                    if (!tag.getIsRelated().equals(YES)) {
                        isApproved = false;
                        break;
                    }
                    isApproved = true;
                }
            }if (thumbnailImage !=null && isApproved){
               isApproved= thumbnailImage.getIsRelated().equals(YES) && thumbnailImage.getIsGoodComposition().equals(YES);
            }
        }

      return isApproved;
    }

    @JsonIgnore
    public boolean onReviewOnProcessIsRunning(){
        boolean onProcess = false;
        if (this.isTitleCorrect == null|| this.isDescriptionSuitable ==null){
          onProcess = true;
        }
        if (reviewImages != null && !onProcess){
            for (ReviewImage image : reviewImages) {
                if (image.getIsRelated() == null || image.getIsGoodComposition()== null){
                    onProcess = true;
                    break;
                }
                onProcess = false;
            }

        } if (reviewLocations != null && !onProcess){
            for (ReviewLocation location : reviewLocations) {
                if (location.getIsContainVideoInfo() == null) {
                    onProcess = true;
                    break;
                }
                onProcess = false;
            }
        } if (reviewTags !=null && !onProcess){
            for (ReviewTag tag : reviewTags) {
                if (tag.getIsRelated() == null) {
                    onProcess = true;
                    break;
                }
                onProcess = false;

            }
        }
        onProcess = thumbnailImage == null || thumbnailImage.getIsGoodComposition() == null || thumbnailImage.getIsRelated() == null && !onProcess;

      return  onProcess;
    }

}
