package com.unriviel.api.controller;

import com.unriviel.api.annotation.APiController;
import com.unriviel.api.dto.MetaDataFilterRequest;
import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.ReviewAssignRequest;
import com.unriviel.api.model.metadata.review.ReviewQsAns;
import com.unriviel.api.service.MataDataFilterService;
import com.unriviel.api.service.ReviewAssignService;
import com.unriviel.api.service.ReviewService;
import com.unriviel.api.service.impl.UserService;
import com.unriviel.api.util.UrlConstrains;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@APiController
@RequestMapping(UrlConstrains.ReviewManagement.ROOT)
public class ReviewController {
    private final ReviewAssignService reviewAssignService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final MataDataFilterService filterService;

    public ReviewController(ReviewAssignService reviewAssignService, ReviewService reviewService, UserService userService, MataDataFilterService filterService) {
        this.reviewAssignService = reviewAssignService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.filterService = filterService;
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

        Pageable pageable = getpagAble(pageNumber,pageSize);
        Response response;
         if (userEmail.equals("admin@gmail.com")){
             response = reviewService.findAll(pageable);
             return ResponseEntity.status((int) response.getStatusCode()).body(response);
         }
        response = reviewService.findAllByReviewerEmail(userEmail, pageable);
         return ResponseEntity.status((int) response.getStatusCode()).body(response);

    }
    @PostMapping(UrlConstrains.ReviewManagement.VIDEOSPOST)
    public ResponseEntity<Object> getAllVideoMetadata(@Valid@RequestBody(required = true) MetaDataFilterRequest request,
                                                      @RequestParam(defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(defaultValue = "20") Integer pageSize){

        Pageable pageable = getpagAble(pageNumber, pageSize);
        if (!request.getSortBy().isEmpty()){
             pageable = getpagAble(pageNumber, pageSize, Sort.by(request.getSortBy()));
        }
        Response  response = filterService.filterMetaDataByTiterOrEmailOrNameOrFullName(request,pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);

    }
    @PostMapping(value = UrlConstrains.ReviewManagement.ON_REVIEW)
    @Operation(description = "Update and start review by videoId")
    public ResponseEntity<Object> onReview (@PathVariable(required = true) String videoId,
                                  @Parameter(description = "Make sure the ans should be this format [yes,no,notSure] otherwise it will be null ")
                                  @RequestBody(required = true) ReviewQsAns reviewQsAns  ){
        if (videoId == null || reviewQsAns == null){
            return ResponseEntity.badRequest().body("videoId ro reviewQsAns can not be null");
        }else {
           Response response = reviewService.onReview(reviewQsAns, videoId);
            return ResponseEntity.status((int) response.getStatusCode()).body(response);
        }
    }

    private Pageable getpagAble(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "20") Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }
    private Pageable getpagAble(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "20") Integer pageSize, Sort sort) {
        return PageRequest.of(pageNumber, pageSize,sort);
    }


}
