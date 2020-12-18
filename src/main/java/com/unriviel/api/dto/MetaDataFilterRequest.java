package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data@AllArgsConstructor@NoArgsConstructor
public class MetaDataFilterRequest {
    @NotEmpty(message = "logged user email can not empty")
    private String  loggedUser;
    private String searchBy;
    private String uploader;
    private String  sortBy;
    private int lastActivity;
    private int reviewProcess;

}
