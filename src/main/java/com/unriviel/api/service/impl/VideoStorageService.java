package com.unriviel.api.service.impl;

import com.unriviel.api.dto.Response;
import com.unriviel.api.dto.VideoResponse;
import com.unriviel.api.exception.FileStorageException;
import com.unriviel.api.exception.MyFileNotFoundException;
import com.unriviel.api.properties.VideoUploadProperties;
import com.unriviel.api.service.VideoMetaDataService;
import com.unriviel.api.util.ResponseBuilder;
import com.unriviel.api.util.UrlConstrains;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
@Log4j2
@Service
public class VideoStorageService {
    private final Path fileStorageLocation;
    private final VideoMetaDataService metaDataService;
    private final UserService userService;
    @Autowired
    public VideoStorageService(VideoUploadProperties fileStorageProperties, VideoMetaDataService metaDataService, UserService userService) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.metaDataService = metaDataService;
        this.userService = userService;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.info("create the file directory first  location not found ");
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Response storeFile(MultipartFile file,String videoId, HttpServletRequest request,String userEmail) {
        // Normalize file name
        boolean isException = false;
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        var replaceFileName = replaceFileName(file, videoId);
        Path filePath = this.fileStorageLocation.resolve(replaceFileName).normalize();
        var path = ServletUriComponentsBuilder.fromRequest(request)
                .replacePath(UrlConstrains.VideoUpload.ROOT).path("/");
        String finalReplaceName = replaceFileName(file,videoId);
//        var responseBefor = new VideoResponse(videoId, path.path(finalReplaceName).toUriString(), false, userEmail);
//
//        metaDataService.saveVideoStatus(responseBefor);

        try {
            // Check if the file's name contains invalid characters
            if(originalFileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, "Sorry! Filename contains invalid path sequence "+originalFileName);
            }
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"file is already exits");
            }else {

                // Copy file to the target location (Replacing existing file with the same name)
                Path targetLocation = this.fileStorageLocation.resolve(finalReplaceName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                var responseAfter = new VideoResponse(videoId, path.path(finalReplaceName).toUriString(), true, userEmail);
                metaDataService.saveVideoStatus(responseAfter);
                log.info("video save successfully");
                return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,
                        "file save successfully", responseAfter);
            }

        }catch (MultipartException e){
            isException = true;
            var responseException = new VideoResponse(videoId, null, false, userEmail);
            metaDataService.saveVideoStatus(responseException);
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Could not store file " + file.getOriginalFilename() + ". Please try again!");
        }
        catch (IOException e) {
            isException = true;
            var responseException = new VideoResponse(videoId, null, false, userEmail);
            metaDataService.saveVideoStatus(responseException);
            e.printStackTrace();
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Could not store file " + file.getOriginalFilename() + ". Please try again!");
        }finally {
            if (isException){
                var responseException = new VideoResponse(videoId, null, false, userEmail);
                metaDataService.saveVideoStatus(responseException);
            }else {
                log.info("video update");
            }
        }


    }
  public Response reStore(MultipartFile file,String videoId,String userEmail,HttpServletRequest req){

      return   storeFile(file,videoId,req,userEmail);
//      var originalFilename = file.getOriginalFilename();
//      assert originalFilename != null;
//      var replaceFileName = replaceFileName(file, videoId);
//      Path filePath = this.fileStorageLocation.resolve(replaceFileName).normalize();
//      var path = ServletUriComponentsBuilder.fromRequest(req)
//              .replacePath(UrlConstrains.VideoUpload.ROOT).path("/").path(replaceFileName);
//      try {
//          if(originalFilename.contains("..")) {
////                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//              return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Sorry! Filename contains invalid path sequence "+originalFilename);
//          }
//          Resource resource = new UrlResource(filePath.toUri());
//          if (!resource.exists()){
//              Path targetLocation = this.fileStorageLocation.resolve(replaceFileName);
//              Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//              var videoResponse = new VideoResponse(videoId, path.toUriString(), true, userEmail);
//              metaDataService.saveVideoStatus(videoResponse);
//              return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
//                      "file saved",videoResponse);
//          }else {
//              return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"File not found! id->"+videoId);
//          }
//      } catch (MalformedURLException e) {
//          e.printStackTrace();
//          throw new  MyFileNotFoundException("file not found "+file.getOriginalFilename() +" ");
//      } catch (IOException e) {
//          e.printStackTrace();
//          throw new  MyFileNotFoundException("file not found "+file.getOriginalFilename());
//      }

  }
    private boolean isFileExits(MultipartFile file, Resource resource) throws IOException {
        if (resource.exists()){
            log.info("resource already exist "+resource.getFilename());
            Path targetLocation = this.fileStorageLocation.resolve(Objects.requireNonNull(resource.getFilename()));
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return true;
        }
        return false;
    }

    private String replaceFileName(MultipartFile file ,String videoId) {
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        return videoId+"."+split[split.length-1];
    }

    public Resource loadFileAsResource(String fileName)  {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
