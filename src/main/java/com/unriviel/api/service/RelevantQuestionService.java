package com.unriviel.api.service;


import com.unriviel.api.dto.RelevenetQSDto;
import com.unriviel.api.dto.Response;

public interface RelevantQuestionService {
    public Response  create(RelevenetQSDto dto);
    public Response getAllQuestions();
    public Response findById(long id);
}
