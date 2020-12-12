package com.unriviel.api.model.metadata.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {
     private AnsStatus isRelated;
     private AnsStatus isGoodComposition;

     public boolean isOk(){
          return this.isRelated.equals(AnsStatus.YES) && this.isGoodComposition.equals(AnsStatus.YES);
     }
}
