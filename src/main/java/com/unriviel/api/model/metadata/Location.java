package com.unriviel.api.model.metadata;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Location implements Serializable  {
    private  String name;
    private  long latitude;
    private long longitude;
    private List<LandMark> landMarks = new ArrayList<>();
}
