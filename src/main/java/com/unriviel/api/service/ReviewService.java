package com.unriviel.api.service;

import com.unriviel.api.dto.Response;
import com.unriviel.api.model.metadata.review.ReviewQsAns;
import org.springframework.data.domain.Pageable;


public interface ReviewService {
    Response onReview(ReviewQsAns reviewQsAns,String videoId);
    Response findAll(Pageable pageable);
    Response findAllByReviewerEmail(String email, Pageable pageable );
    Response finAllByUploaderEmail(String email,Pageable pageable);
}
