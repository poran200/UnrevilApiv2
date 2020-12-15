package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class MetaDataFilterRequest {
    private String asinine;
    private String uploader;
    private Date date;
    private int lastActivity;
}
