package com.unriviel.api.model.metadata.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage implements Serializable {
     private AnsStatus isRelated;
     private AnsStatus isGoodComposition;

     @JsonIgnore
     public boolean isOk(){
          return this.isRelated.equals(AnsStatus.YES) && this.isGoodComposition.equals(AnsStatus.YES);
     }
}
