package com.unriviel.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data@AllArgsConstructor@NoArgsConstructor
public class MetaDataFilterRequest {
    @NotEmpty(message = "logged user email can not empty")
    private String  loggedUser;
    private String searchBy;
    private String uploader;
    private String  sortBy;
    private int lastActivity;
    @Min(value = 0 ,message = "the default value is zero  make sure that ")
    @Max(value = 3,message = "max value for review  process 3")
    private int reviewProcess;

}
