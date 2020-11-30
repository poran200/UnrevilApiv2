package com.unriviel.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProfileDto {
    private String profileImageUrl;
    private List<String> socialMediaLinks;
    private List<RelevenqsAnsDto> relevantQsAnsList;

}
