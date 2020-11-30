package com.unriviel.api.service.impl;


import com.unriviel.api.dto.Response;
import com.unriviel.api.model.ImageFile;
import com.unriviel.api.repository.ImageRepository;
import com.unriviel.api.service.ImageStorageService;
import com.unriviel.api.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    private final ImageRepository imageRepository;

    public ImageStorageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Response saveImage(MultipartFile file) {
        String fileNme = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try{
            if (fileNme.contains("..")){
                return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Sorry the file name contains invalid path sequence");
            }

//            Encoder encoder = Base64.getEncoder();

            ImageFile imageFile = new ImageFile(null,fileNme,file.getContentType(),file.getBytes());
            ImageFile saveFile = imageRepository.save(imageFile);
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,"File uploaded successfully ",saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Could not store the file "+fileNme+" please try again !");
        }
    }

    @Override
    public Response getImageById(String imageId) {
        Optional<ImageFile> optionalImageFile = imageRepository.findById(imageId);
        if (optionalImageFile.isPresent()){
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"File found !",optionalImageFile.get());
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"file not found imageId = "+imageId);
    }
}
