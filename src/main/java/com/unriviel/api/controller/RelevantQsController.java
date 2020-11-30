package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.Response;
import com.unriviel.api.service.RelevantQuestionService;
import com.unriviel.api.util.UrlConstrains;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@APiController
@RequestMapping(UrlConstrains.ReleventQs.ROOT)
public class RelevantQsController {
    private final RelevantQuestionService relevantQuestionService;

    public RelevantQsController(RelevantQuestionService relevantQuestionService) {
        this.relevantQuestionService = relevantQuestionService;
    }
    @GetMapping(UrlConstrains.ReleventQs.All)
    @Operation(description = "Find All relevant question")
    public ResponseEntity<Object> getAll(){
        Response response = relevantQuestionService.getAllQuestions();
        return  ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

