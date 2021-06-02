package com.unriviel.api.service;

import com.unriviel.api.dto.VideoInfo;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class StringMatchTest {
    @Test
    void matched() {
        String s = "mov,mp4,mvi";
        var split = s.split(",");
        String s2 = "mp4";
        for (String string :
                split) {
            if (string.equalsIgnoreCase(s2)){
                System.out.println("string = " + string);
            }
        }


    }

    @Test
    void infotest() {
        VideoInfo info = new VideoInfo();
        info.setVideoEncoding("mp4");
        var split = "mov,mp4,mvi".split(",");
        boolean isEncodingMatching = false;
        for (String s : split) {
            var videoEncoding = info.getVideoEncoding().toString();
            if (s.compareToIgnoreCase(videoEncoding) == 0) {
                System.out.println(("video encoding match = " + info.getVideoEncoding()));
                isEncodingMatching = true;
                break;
            }
        }
        System.out.println("isEncodingMatching = " + isEncodingMatching);
    }
    @Test
    void getExtension(){
        String fileName = "WhatsApp Video 2020-12-14 at 12.18.12 PM.mp4";
        var names = fileName.split("\\.");
        System.out.println("names = " + names.length);
        var name = names[names.length - 1];
        System.out.println("name = " + name);
        var mp4 = name.equalsIgnoreCase("mp4");
        assertTrue(mp4);
    }
}
