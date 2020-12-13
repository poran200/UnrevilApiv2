package com.unriviel.api.model.metadata;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Images implements Serializable {
    String  url;
    String cropImageUrl;
    Coordinate first;
    Coordinate second;
    Coordinate third;
    Coordinate fourth;
}
