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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

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
    private String VIDEO_TYPE;
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

        Path filePath = this.fileStorageLocation.resolve(videoId).normalize();

        try {
            URL url = new URL(videoUrl);
            try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(filePath.toString());
                 FileChannel fileChannel = fileOutputStream.getChannel()) {
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                var checkMd5Hash = checkMd5Hash(filePath.toString());
                if (!checkMd5Hash){
                   VideoInfo info = new VideoInfo();
                    info.setValid(false);
                    info.setStatusMassage(VideoInfo.URL_INVALID);
                    metaDataService.saveVideoInfo(videoId,info);
                }
            } catch (IOException | NoSuchAlgorithmException e) {
                VideoInfo videoInfo = new VideoInfo();
                 videoInfo.setValid(false);
                 videoInfo.setStatusMassage(VideoInfo.URL_INVALID);
                 metaDataService.saveVideoInfo(videoId,videoInfo);
                log.error(e.getMessage());

            }
        } catch (MalformedURLException e) {
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setValid(false);
            videoInfo.setStatusMassage(VideoInfo.URL_INVALID);
            metaDataService.saveVideoInfo(videoId,videoInfo);
            log.error(e.getMessage());
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

    private boolean checkMd5Hash(String filename) throws IOException, NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Files.readAllBytes(fileStorageLocation.resolve(filename).normalize()));
        byte[] digest = md.digest();
        String myChecksum = DatatypeConverter.printHexBinary(digest);
        System.out.println("myChecksum = " + myChecksum);
        return myChecksum.equalsIgnoreCase(FILE_MD5_HASH);
    }

    public void extractMetadata(String videoId,String fileName){
        FFprobe ffprobe = null;
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
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setValid(false);
            metaDataService.saveVideoInfo(videoId,videoInfo);
            e.printStackTrace();
        }
        if (probeResult != null){
            FFmpegFormat format = probeResult.getFormat();
            System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
                    format.filename,
                    format.format_name,
                    format.duration
            );

            FFmpegStream stream = probeResult.getStreams().get(0);
            System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
                    stream.codec_long_name,
                    stream.width,
                    stream.height
            );
        }

    }
    public VideoInfo checkValidation(VideoInfo info){
        var first = Arrays.stream(VIDEO_TYPE.split(","))
                .filter(s -> s.equalsIgnoreCase(info.getVideoEncoding()))
                .findFirst();
         if (first.isEmpty()) {
             info.setValid(false);
             info.setStatusMassage(VideoInfo.VIDEO_TYPE_NOT_VALID);
             return info;
         }

        var height = info.getHeight();
        var width = info.getWidth();
        if((width*16)/(height*9) != 1){
          info.setValid(false);
          info.setStatusMassage(VideoInfo.ASPECT_RATIO_NOT_VALID);
          return info;
        }
        if (info.getVideoFps()< 24){
            info.setValid(false);
            info.setStatusMassage(VideoInfo.VIDEO_FPS_NOT_GOOD);
            return info;
        }
        info.setValid(true);
        return info;
    }

}



