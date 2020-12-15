package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.ReviewAssignRequest;
import com.unriviel.api.service.ReviewAssignService;
import com.unriviel.api.service.ReviewService;
import com.unriviel.api.service.impl.UserService;
import com.unriviel.api.util.UrlConstrains;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@APiController
@RequestMapping(UrlConstrains.ReviewManagement.ROOT)
public class ReviewController {
    private final ReviewAssignService reviewAssignService;
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewAssignService reviewAssignService, ReviewService reviewService, UserService userService) {
        this.reviewAssignService = reviewAssignService;
        this.reviewService = reviewService;
        this.userService = userService;
    }
    @PostMapping(UrlConstrains.ReviewManagement.ASSIGN)
    public ResponseEntity<Object> assignReviewer(@Valid @RequestBody ReviewAssignRequest request){
        var response = reviewAssignService.assign(request.getVideoId(), request.getReviewerEmail());
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(UrlConstrains.ReviewManagement.VIDEOS)
    public ResponseEntity<Object> getAllVideoMetadata(@Valid @PathVariable(required = true) String userEmail,
                                                      @RequestParam(defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(defaultValue = "20") Integer pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Response response;
         if (userEmail.equals("admin@gmail.com")){
             response = reviewService.findAll(pageable);
             return ResponseEntity.status((int) response.getStatusCode()).body(response);
         }
        response = reviewService.findAllByReviewerEmail(userEmail, pageable);
         return ResponseEntity.status((int) response.getStatusCode()).body(response);

    }
}