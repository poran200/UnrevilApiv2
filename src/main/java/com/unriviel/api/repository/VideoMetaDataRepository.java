package com.unriviel.api.repository;

import com.unriviel.api.model.metadata.VideoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoMetaDataRepository extends JpaRepository<VideoMetaData,String> {
}
