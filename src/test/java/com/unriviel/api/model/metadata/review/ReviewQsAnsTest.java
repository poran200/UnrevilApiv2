package com.unriviel.api.model.metadata.review;

import com.unriviel.api.model.metadata.VideoMetaData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.unriviel.api.model.metadata.review.AnsStatus.YES;

class ReviewQsAnsTest {

    @Test
    void isApproved() {
        var images = new ArrayList<ReviewImage>();
        images.add(new ReviewImage("url",YES, YES));
        var locations = new ArrayList<ReviewLocation>();
        locations.add(new ReviewLocation("dhaka",YES));
        var tags = new ArrayList<ReviewTag>();
        tags.add(new ReviewTag("tag",YES));
        var reviewQsAns = new ReviewQsAns();
        reviewQsAns.setIsDescriptionSuitable(null);
        reviewQsAns.setIsTitleCorrect(YES);
        reviewQsAns.setReviewLocations(locations);
        reviewQsAns.setReviewTags(tags);
        reviewQsAns.setReviewImages(images);
        var reviewOnProcess = reviewQsAns.onReviewOnProcessIsRunning();
        var approved = reviewQsAns.isApproved();
        System.out.println("approved = " + approved);
        System.out.println("reviewOnProcessIsRunning = " + reviewOnProcess);
        VideoMetaData videoMetaData  = new VideoMetaData();
        videoMetaData.setReviewQsAns(reviewQsAns);
        videoMetaData.reviewStatusSet();
        videoMetaData.setApprovedStatus();
        System.out.println("video process ="+ videoMetaData.getReviewProcess().toString());
        System.out.println("video is approved="+ videoMetaData.getReviewStatus());

    }
}