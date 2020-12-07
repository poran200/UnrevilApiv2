package com.unriviel.api.repository;

import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData,String> {

//    Page<VideoMetaData> findAllByUserEmailAndOrderByCreatedAtDesc(String userEmail, Pageable pageable);

//    Page<VideoMetaData> findAllByUserEmailAndOrderByCreatedAt(String eamil, Pageable pageable);
      Page<VideoMetaData> findAllByUserEmail(String email, Pageable pageable);
//      List<VideoMetaData>findAllTagsStartingWith(String tag);
      Page<VideoMetaData>findAllByTagsStartingWith(String tag,Pageable pageable);
//      @Query("select VideoMetaData.tags from VideoMetaData")
//      List<String>allTag();
}
