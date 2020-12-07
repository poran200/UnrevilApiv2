package com.unriviel.api.repository;

import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData,String> {

//    Page<VideoMetaData> findAllByUserEmailAndOrderByCreatedAtDesc(String userEmail, Pageable pageable);

//    Page<VideoMetaData> findAllByUserEmailAndOrderByCreatedAt(String eamil, Pageable pageable);
}
