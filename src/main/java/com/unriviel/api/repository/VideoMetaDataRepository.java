package com.unriviel.api.repository;

import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData,String> {


      Page<VideoMetaData>findAllByUploaderEmailOrderByCreatedAtDesc(String email, Pageable pageable);
      Page<VideoMetaData>findAllByTagsStartingWith(String tag,Pageable pageable);
      Page<VideoMetaData>findAllByReviewerEmailAndReviewProcessOrderByCreatedAtDesc(String reviewerEmail, ReviewStatus reviewStatus,Pageable pageable);
//      Page<VideoMetaData>findAllByReviewerEmailAndOrderByCreatedAtDesc(String reviewerEmail,Pageable pageable);
      Page<VideoMetaData>findAllByReviewerEmailOrderByCreatedAtDesc(String reviewerEmail,Pageable pageable);
//      @Query(value = "select  * from VideoMetaData where VideoMetaData .uploader = :email")
//      Page<VideoMetaData>findAllFilter(String reviewerEmail,Pageable pageable);

      Page<VideoMetaData> findAllByTitleOrReviewerUsernameOrReviewerEmailOrReviewerFullNameOrderByCreatedAtDesc(String title, String reviewer_username, String reviewer_email,String reviewer_fullName, Pageable pageable);
      Page<VideoMetaData> findAllByTitleOrReviewerUsernameOrReviewerEmailOrReviewerFullNameAndUploaderEmailOrderByCreatedAtDesc(String title, String reviewer_username,
                                                                                                            String reviewer_email,String reviewer_fullName,String uploaderEmail, Pageable pageable);
      Integer countAllByReviewProcessAndReviewerEmail(ReviewStatus reviewStatus,String reviewerEmail);
      Integer countAllByReviewProcessAndUploaderEmail(ReviewStatus reviewStatus,String reviewerEmail);
      Integer countAllByReviewProcess(ReviewStatus reviewProcess);
      //for admin //
//      Page<VideoMetaData> findAllByReviewProcessI
}
