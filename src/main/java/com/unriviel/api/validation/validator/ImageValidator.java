package com.unriviel.api.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageValidator{

    private final Pattern pattern;
    private  Matcher matcher;

    private static final String IMAGE_PATTERN =
            "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$";

    public ImageValidator(){
        pattern = Pattern.compile(IMAGE_PATTERN);
    }

    /**
     * Validate image with regular expression
     * @param image image for validation
     * @return true valid image, false invalid image
     */
    public boolean  validate(final String image){
        matcher = pattern.matcher(image);
        return matcher.matches();

    }
}
