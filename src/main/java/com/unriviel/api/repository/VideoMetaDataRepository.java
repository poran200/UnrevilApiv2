package com.unriviel.api.repository;

import com.unriviel.api.enums.ReviewStatus;
import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData,String> {


      Page<VideoMetaData> findAllByUploaderEmailOrderByCreatedAtDesc(String email, Pageable pageable);
      Page<VideoMetaData>findAllByTagsStartingWith(String tag,Pageable pageable);
      Page<VideoMetaData>findAllByReviewerEmailAndReviewProcessOrderByCreatedAtDesc(String reviewerEmail, ReviewStatus reviewStatus,Pageable pageable);
//      Page<VideoMetaData>findAllByReviewerEmailAndOrderByCreatedAtDesc(String reviewerEmail,Pageable pageable);
      Page<VideoMetaData>findAllByReviewerEmailOrderByCreatedAtDesc(String reviewerEmail,Pageable pageable);

}
