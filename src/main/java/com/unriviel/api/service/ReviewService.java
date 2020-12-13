package com.unriviel.api.service;

import com.unriviel.api.dto.Response;
import com.unriviel.api.model.metadata.review.ReviewQsAns;

public interface ReviewService {
    Response onReview(ReviewQsAns reviewQsAns,String videoId);
}
