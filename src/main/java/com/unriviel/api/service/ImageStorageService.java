package com.unriviel.api.service;


import com.unriviel.api.dto.Response;
import org.springframework.web.multipart.MultipartFile;


public interface ImageStorageService {
  public Response saveImage(MultipartFile file);
  public Response getImageById(String imageId);
}
