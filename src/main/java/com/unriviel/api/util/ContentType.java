package com.unriviel.api.util;
public enum ContentType {

    VIDEO ("VD"),
    AUDIO ("AD"),
    IMAGE ("IM"),
    TEXT ("TX");

    public final String contentType;

    ContentType(String contentType) {

        this.contentType = contentType;
    }

    public String getContentType()
    {
        return this.contentType;
    }
}