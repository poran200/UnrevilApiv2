package com.unriviel.api.model.metadata.review;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.unriviel.api.model.metadata.review.AnsStatus.YES;

class ReviewQsAnsTest {

    @Test
    void isApproved() {
        var images = new ArrayList<ReviewImage>();
        images.add(new ReviewImage(YES, YES));
        var locations = new ArrayList<ReviewLocation>();
        locations.add(new ReviewLocation("dhaka",YES));
        var tags = new ArrayList<ReviewTag>();
        tags.add(new ReviewTag("tag",YES));
        var reviewQsAns = new ReviewQsAns();
        reviewQsAns.setIsDescriptionSuitable(YES);
        reviewQsAns.setIsTitleCorrect(null);
        reviewQsAns.setReviewLocations(locations);
        reviewQsAns.setReviewTags(tags);
        reviewQsAns.setReviewImages(images);
        var reviewOnProcess = reviewQsAns.onReviewOnProcessIsRunning();
        var approved = reviewQsAns.isApproved();
        System.out.println("approved = " + approved);
        System.out.println("reviewOnProcessIsRunning = " + reviewOnProcess);

    }
}