package com.unriviel.api.service.impl;

import com.unriviel.api.dto.VideoInfo;
import com.unriviel.api.exception.FileStorageException;
import com.unriviel.api.exception.MyFileNotFoundException;
import com.unriviel.api.properties.VideoDownloadsProperties;
import com.unriviel.api.service.VideoMetaDataService;
import lombok.extern.log4j.Log4j2;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class DownloadVideoStorageService {
    private final Path fileStorageLocation;
    private final VideoMetaDataService metaDataService;
    private final UserService userService;
    static String FILE_MD5_HASH = "6cb91af4ed4c60c11613b75cd1fc6116";
    @Value("${app.ffpobe.path}")
    private String FFPROBE_PATH;
    @Value("${app.video.type}")
    private  String VIDEO_TYPE;
    private  boolean delete = false;
    @Autowired
    public DownloadVideoStorageService(VideoDownloadsProperties fileStorageProperties, VideoMetaDataService metaDataService, UserService userService) {
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


    public void storeFile(String videoUrl, String videoId) {
        boolean isException = false;
        Path filePath = this.fileStorageLocation.resolve(videoId).normalize();
            int CONNECT_TIMEOUT = 10000;
            int READ_TIMEOUT = 10000;
            try {
                log.info("file download started "+ new Date().getTime());
                FileUtils.copyURLToFile(new URL(videoUrl), new File(filePath.normalize().toString()),
                        CONNECT_TIMEOUT, READ_TIMEOUT);
            } catch (IOException e) {
                isException = true;
                log.error(e.getMessage());
            }finally {
                if (isException){
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setValid(false);
                    videoInfo.setStatusMassage(VideoInfo.URL_INVALID);
                    metaDataService.saveVideoInfo(videoId,videoInfo);
                    log.info("url is not valid or not downloadable ");
                }
                log.info("download successfully vName= "+videoId);
            }

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
        return split[0]= videoId+"."+split[1];
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


    public Optional<VideoInfo> extractMetadata(String videoId, String fileName){
        FFprobe ffprobe = null;
        boolean isIoException= false;
        try {
            ffprobe = new FFprobe(FFPROBE_PATH);
            log.info("ffprobe path find and start read");
        } catch (IOException e) {
            log.info("ffprobe path not found");
            e.printStackTrace();
        }
        FFmpegProbeResult probeResult = null;
        try {
            if (ffprobe != null) {
                probeResult = ffprobe.probe(fileStorageLocation.resolve(fileName).toString());
                log.info("extract meta data successfully");
            }
        } catch (IOException e) {
            isIoException = true;
            e.printStackTrace();
        }finally {
            if (isIoException){
                VideoInfo videoInfo = new VideoInfo();
                videoInfo.setValid(false);
                videoInfo.setStatusMassage(VideoInfo.URL_INVALID);
                metaDataService.saveVideoInfo(videoId,videoInfo);
                log.error("ioException file no readable");
            }
            log.info("meta data extract start ");
        }
        if (probeResult != null){
            FFmpegFormat format = probeResult.getFormat();
//            System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
//                    format.filename,
//                    format.format_name,
//                    format.duration
//            );
          VideoInfo videoInfo = new VideoInfo();
            videoInfo.setVideoName(videoId);
            videoInfo.setVideoType(format.format_name);
            videoInfo.setVideoEncoding(format.format_long_name.replaceAll(".+/","").toLowerCase().trim());
            videoInfo.setVideoDuration((long) format.duration);
            videoInfo.setVideoSize(format.size);
            FFmpegStream stream = probeResult.getStreams().get(0);
              videoInfo.setWidth(stream.width);
              videoInfo.setHeight(stream.height);
              videoInfo.setVideoFps(stream.avg_frame_rate.intValue());
            log.info("video info from ffmpeg = "+videoInfo.toString());
            var info = checkValidation(videoInfo);
            if (!info.isValid()) deleted(videoId);
            return Optional.of(info);
//            System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
//                    stream.codec_long_name,
//                    stream.width,
//                    stream.height
//            );

        }
        return Optional.empty();
    }
    public VideoInfo checkValidation(VideoInfo info){

//        var split = VIDEO_TYPE.split(",");
         var split = "mov,mp4,mvi".split(",");
        boolean isEncodingMatching = false;
        for (String s : split) {
            log.info("video type = " + s + " and encoding = " + info.getVideoEncoding());
            var videoEncoding = info.getVideoEncoding();
            if (s.compareToIgnoreCase(videoEncoding) == 0) {
                log.info("video encoding match = " + info.getVideoEncoding());
                isEncodingMatching = true;
                break;
            }
        }
        if (!isEncodingMatching){
            info.setValid(false);
            info.setStatusMassage(VideoInfo.VIDEO_TYPE_NOT_VALID);
        }


        var height = info.getHeight();
        var width = info.getWidth();
        if((width*16)/(height*9) != 1){
          info.setValid(false);
          info.setStatusMassage(VideoInfo.ASPECT_RATIO_NOT_VALID);
          log.info(VideoInfo.ASPECT_RATIO_NOT_VALID);
          return info;
        }
        if (!(info.getVideoFps() >= 24) && !(info.getVideoFps()<=30) ){
            info.setValid(false);
            info.setStatusMassage(VideoInfo.VIDEO_FPS_NOT_GOOD);
            log.info(VideoInfo.VIDEO_FPS_NOT_GOOD);
            return info;
        }
        info.setValid(true);
        return info;
    }
    public void deleted(String fileName){
        try {
            FileUtils.touch(new File(fileStorageLocation.resolve(fileName).normalize().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.forceDelete(FileUtils.getFile(fileStorageLocation.resolve(fileName).normalize().toString()));
            log.info("file deleted filename :"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



