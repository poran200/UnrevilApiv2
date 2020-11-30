package com.unriviel.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class Response   {
     @JsonInclude(JsonInclude.Include.ALWAYS)
     private long timestamp;
     @JsonInclude(JsonInclude.Include.ALWAYS)
     private  long statusCode;
     @JsonInclude(JsonInclude.Include.ALWAYS)
     private String status;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     private String massage;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     private Object content;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     @JsonIgnore
     private  int numberOfElement;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     @JsonIgnore
     private  long rowCount;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     private List<ErrorResponseDto> errors;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     private List<Object> contentList;
     @JsonInclude(JsonInclude.Include.NON_NULL)
     private Page<?> page;


}
