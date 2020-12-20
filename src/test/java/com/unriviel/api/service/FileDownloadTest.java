//package com.unriviel.api.service;
//
//
//import net.bramp.ffmpeg.FFprobe;
//import net.bramp.ffmpeg.probe.FFmpegFormat;
//import net.bramp.ffmpeg.probe.FFmpegProbeResult;
//import net.bramp.ffmpeg.probe.FFmpegStream;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.xml.bind.DatatypeConverter;
//import java.io.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.nio.channels.Channels;
//import java.nio.channels.FileChannel;
//import java.nio.channels.ReadableByteChannel;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class FileDownloadTest {
//    String localiseName = "upload-dir/demo.mp4";
//    static String FILE_MD5_HASH = "6cb91af4ed4c60c11613b75cd1fc6116";
//    @Test
//    void dawonload() throws IOException {
//
//        URL url = null;
//        try {
//            url = new URL("https://www.youtube.com/watch?v=2NwK3bxISCM");
//            var file = url.getFile();
//            var headerFields = url.openConnection().getHeaderFields();
//            System.out.println("headerFields = " + headerFields);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        assert url != null;
//        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//             FileOutputStream fileOutputStream = new FileOutputStream(Path.of(localiseName).normalize().toString());
//             FileChannel fileChannel = fileOutputStream.getChannel()) {
//            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("e = " + e.getMessage());
//        }
//    }
//    private boolean checkMd5Hash(String filename) throws IOException, NoSuchAlgorithmException {
//
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(Files.readAllBytes(Paths.get(filename)));
//        byte[] digest = md.digest();
//        String myChecksum = DatatypeConverter.printHexBinary(digest);
//        System.out.println("myChecksum = " + myChecksum);
//        return myChecksum.equalsIgnoreCase(FILE_MD5_HASH);
//    }
//
//    @Test
//    void readFile() throws IOException {
//        FFprobe ffprobe = new FFprobe("C:\\Users\\Poran chowdury\\Downloads\\ffprobe-4.2.1-win-64\\ffprobe.exe");
//        FFmpegProbeResult probeResult = ffprobe.probe("upload-videos\\VD202012111643171001.mp4");
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
//    void endode() {
//
//    }
//}
