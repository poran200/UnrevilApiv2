package com.unriviel.api.dto;

import com.unriviel.api.model.RelevantQuestion;
import lombok.Data;
@Data
public class RelevenqsAnsDto {
//    private long id;
    private RelevantQuestion question;
    private String answer;
}
