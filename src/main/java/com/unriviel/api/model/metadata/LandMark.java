package com.unriviel.api.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandMark  implements Serializable {

    private  String landMarkName;
    private  long latitude;
    private long longitude;
}
