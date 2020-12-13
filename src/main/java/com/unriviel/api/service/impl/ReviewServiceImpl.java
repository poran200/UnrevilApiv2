package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.model.metadata.review.ReviewQsAns;
import com.unriviel.api.repository.ReviewQsAnsRepository;
import com.unriviel.api.repository.VideoMetaDataRepository;
import com.unriviel.api.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final VideoMetaDataRepository metaDataRepository;
    private final ReviewQsAnsRepository ansRepository;
    public ReviewServiceImpl(VideoMetaDataRepository metaDataRepository, ReviewQsAnsRepository ansRepository) {
        this.metaDataRepository = metaDataRepository;
        this.ansRepository = ansRepository;
    }

    @Override
    public Response onReview(ReviewQsAns reviewQsAns, String videoId) {
        var metaData = metaDataRepository.findById(videoId);
        if (metaData.isPresent()){
            var videoMetaData = metaData.get();
            if (videoMetaData.getReviewQsAns() == null) {
                var review = ansRepository.save(reviewQsAns);
                videoMetaData.setReviewQsAns(review);
                videoMetaData.reviewStatusSet();
                videoMetaData.setApprovedStatus();
            }
        }
        return null;
    }
}
