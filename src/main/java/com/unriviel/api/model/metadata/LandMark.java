package com.unriviel.api.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandMark {
    private  String landMarkName;
    private  long latitude;
    private long longitude;
}
