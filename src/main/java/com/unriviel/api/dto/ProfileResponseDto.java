package com.unriviel.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unriviel.api.model.RelevantQsAns;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ProfileResponseDto {
    private long id;
    @NotEmpty
    private String profileImageUrl;
    @NotEmpty
    private List<String> socialMediaLinks = new ArrayList<>();
    @JsonIgnoreProperties({"profile"})
    private List<RelevantQsAns> relevantQsAnsList;
    private  int totalUploadContent;
    private  int totalApproved;
}
