package com.unriviel.api.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class Audio {
    private  boolean isNoAudio;
    private  boolean isUnlicensed;
    private  String audioLicense;

}
