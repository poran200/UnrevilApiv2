//package com.unriviel.api.service;
//
//
//import net.bramp.ffmpeg.FFprobe;
//import net.bramp.ffmpeg.probe.FFmpegFormat;
//import net.bramp.ffmpeg.probe.FFmpegProbeResult;
//import net.bramp.ffmpeg.probe.FFmpegStream;
//import org.apache.commons.io.FileUtils;
//import org.junit.jupiter.api.Test;
//
//import javax.xml.bind.DatatypeConverter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.channels.Channels;
//import java.nio.channels.FileChannel;
//import java.nio.channels.ReadableByteChannel;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class FileDownloadTest {
//    String localiseName = "upload-videos/demo.mp4";
//    static String FILE_MD5_HASH = "6cb91af4ed4c60c11613b75cd1fc6116";
//    @Test
//    void dawonload() throws IOException {
//         boolean delete = false;
//        URL url = null;
//        try {
//            url = new URL("https://vod-progressive.akamaized.net/exp=1608636025~acl=%2A%2F1264741754.mp4%2A~hmac=41bceab1d8765b02334e0b0a1cee6e7d6d40744e5b6e15a3a61f18c38869c17e/vimeo-prod-skyfire-std-us/01/4835/12/324179796/1264741754.mp4?download=1&filename=Pexels+Videos+2021532.mp4");
//             var contentEncoding = url.openConnection().getContentEncoding();
//            System.out.println("contentEncoding = " + contentEncoding);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        assert url != null;
//        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//             FileOutputStream fileOutputStream = new FileOutputStream(Path.of(localiseName).normalize().toString());
//             FileChannel fileChannel = fileOutputStream.getChannel()) {
//            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//            if (checkMd5Hash()){
//                System.out.println("file downloaded");
//            }else{
//                System.out.println("file not downloadable");
////               delete = true;
//            }
//
//        } catch (IOException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            System.out.println("e = " + e.getMessage());
//        }
////        if (delete)   Files.delete(Path.of(localiseName).normalize());
//    }
//    private boolean checkMd5Hash() throws IOException, NoSuchAlgorithmException {
//
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(Files.readAllBytes(Paths.get("upload-videos/demo.mp4")));
//        byte[] digest = md.digest();
//        String myChecksum = DatatypeConverter.printHexBinary(digest);
//        System.out.println("myChecksum = " + myChecksum);
//        return myChecksum.equalsIgnoreCase(FILE_MD5_HASH);
//    }
//
//    @Test
//    void readFile() throws IOException {
//        FFprobe ffprobe = new FFprobe("C:\\Users\\Poran chowdury\\Downloads\\ffprobe-4.2.1-win-64\\ffprobe.exe");
//        FFmpegProbeResult probeResult = ffprobe.probe("upload-videos\\demo.mp4");
//
//        FFmpegFormat format = probeResult.getFormat();
//        System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs",
//                format.filename,
//                format.format_name,
//                format.duration
//        );
//
//        FFmpegStream stream = probeResult.getStreams().get(0);
//        System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx",
//                stream.codec_long_name,
//                stream.width,
//                stream.height
//        );
//    }
//
//    @Test
//    void useapachecommon() {
//        int CONNECT_TIMEOUT = 10000;
//        int READ_TIMEOUT = 10000;
//        try {
//
//            FileUtils.copyURLToFile(new URL("https://vod-progressive.akamaized.net/exp=1608499934~acl=%2A%2F1063777479.mp4%2A~hmac=80e0e0929c0ec1b3ba6770b7e9a584d7198d076e594f45601df53207f7d4af7d/vimeo-prod-skyfire-std-us/01/1604/11/283023712/1063777479.mp4?download=1&filename=Pexels+Videos+1295231.mp4"), new File(localiseName),
//                    CONNECT_TIMEOUT, READ_TIMEOUT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void readfileData() throws IOException {
//        URL website = new URL("http://34.105.245.231:9004/api/v1/video/VD20201219130601561.mp4");
//        try (InputStream in = website.openStream()) {
//            Path path = Paths.get("upload-videos/demo.mp4").normalize();
//            Files.copy(in,path , StandardCopyOption.REPLACE_EXISTING);
//        }
//
//    }
//}
