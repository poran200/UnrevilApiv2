package com.unriviel.api.repository;

import com.unriviel.api.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageFile, String> {

}
