package com.unriviel.api.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audio {
    private  boolean isNoAudio;
    private  boolean isUnlicensed;
    private  String audioLicense;

}
