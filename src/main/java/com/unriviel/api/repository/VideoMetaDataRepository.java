package com.unriviel.api.repository;

import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData,String> {


      Page<VideoMetaData>findAllByUploaderEmailOrderByCreatedAtDesc(String email, Pageable pageable);
      Page<VideoMetaData>findAllByTagsStartingWith(String tag,Pageable pageable);
      Page<VideoMetaData>findAllByReviewerEmailAndReviewProcessOrderByCreatedAtDesc(String reviewerEmail, ReviewStatus reviewStatus,Pageable pageable);
//      Page<VideoMetaData>findAllByReviewerEmailAndOrderByCreatedAtDesc(String reviewerEmail,Pageable pageable);
      Page<VideoMetaData>findAllByReviewerEmailOrderByCreatedAtDesc(String reviewerEmail,Pageable pageable);
//      @Query(value = "select  * from VideoMetaData where VideoMetaData .uploader = :email")
//      Page<VideoMetaData>findAllFilter(String reviewerEmail,Pageable pageable);

      //only scarch key word
      Page<VideoMetaData> findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithOrderByCreatedAtDesc(String title, String reviewer_username, String reviewer_email,String reviewer_fullName, Pageable pageable);
      //Scarch and last activty
      Page<VideoMetaData> findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithAndCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(String title, String reviewer_username,  String reviewer_email, String reviewer_fullName, Date today, Date beforeDay, Pageable pageable);
      //scarch and last acitvity and review process
      Page<VideoMetaData> findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithAndCreatedAtLessThanEqualAndCreatedAtGreaterThanEqualAndReviewProcess(String title, String reviewer_username, String reviewer_email, String reviewer_fullName, Date today, Date beforeDay, ReviewStatus reviewProcess, Pageable pageable);
      //S and R_process
      Page<VideoMetaData> findAllByTitleStartingWithOrReviewerUsernameStartingWithOrReviewerEmailStartingWithOrReviewerFullNameStartingWithAndReviewProcessOrderByCreatedAtDesc(String title, String reviewer_username, String reviewer_email, String reviewer_fullName, ReviewStatus reviewProcess, Pageable pageable);
      //Su/ skip now
      Page<VideoMetaData> findAllByTitleOrReviewerUsernameOrReviewerEmailOrReviewerFullNameAndUploaderEmailOrderByCreatedAtDesc(String title, String reviewer_username,
                                                                                                            String reviewer_email,String reviewer_fullName,String uploaderEmail, Pageable pageable);
      Integer countAllByReviewProcessAndReviewerEmail(ReviewStatus reviewStatus,String reviewerEmail);
      Integer countAllByReviewProcessAndUploaderEmail(ReviewStatus reviewStatus,String reviewerEmail);
      Integer countAllByReviewProcess(ReviewStatus reviewProcess);
      //for admin //
      //only review
      Page<VideoMetaData> findAllByReviewProcess(ReviewStatus reviewProcess, Pageable pageable);
      //only last activity
      Page<VideoMetaData> findAllByCreatedAtBetween(Date today, Date beforeAgo, Pageable pageable);
      //review and last acitvity
      Page<VideoMetaData> findAllByReviewProcessAndCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(ReviewStatus reviewProcess, Date today, Date beforeAgo, Pageable pageable);
      Page<VideoMetaData> findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(Date createdAt, Date createdAt2, Pageable pageable);
}
