package com.unriviel.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;


public final class VideoIdGenerator {
    public static String generateUUID(ContentType cnt, int initNumber) {
        LocalDateTime now = LocalDateTime.now();
        String basicIsoTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        AtomicInteger seq = new AtomicInteger(initNumber);

        return cnt.getContentType().concat(basicIsoTime).concat(Integer.toString(seq.incrementAndGet()));
    }
}
