package com.unriviel.api.model.metadata.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

import static com.unriviel.api.model.metadata.review.AnsStatus.YES;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewQsAns {
    private int id;
    @Enumerated(value = EnumType.STRING)
    private AnsStatus isTitleCorrect;
    @Enumerated(value = EnumType.STRING)
    private AnsStatus isDescriptionSuitable;
    @Enumerated(value = EnumType.STRING)
    private AnsStatus isAdultContent;
    List<ReviewImage> reviewImages = new ArrayList<>();
    List<ReviewTag> reviewTags = new ArrayList<>();
    List<ReviewLocation> reviewLocations = new ArrayList<>() ;
    List<Integer> audioOptionList = new ArrayList<>();
    String comment;

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
            }
        }

      return isApproved;
    }

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

      return  onProcess;
    }

}
