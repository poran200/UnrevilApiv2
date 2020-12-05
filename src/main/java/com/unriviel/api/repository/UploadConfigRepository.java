package com.unriviel.api.repository;

import com.unriviel.api.model.uploadconfig.UploadConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadConfigRepository extends JpaRepository<UploadConfig,Long> {
}
