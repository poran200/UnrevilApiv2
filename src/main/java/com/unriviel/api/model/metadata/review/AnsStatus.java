package com.unriviel.api.model.metadata.review;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
public enum  AnsStatus implements Serializable  {

    @JsonProperty(value = "yes")
    YES("yes"),
    @JsonProperty(value = "no")
    NO("no"),
    @JsonProperty(value = "notSure")
    NOT_SURE("notSure"),
    @JsonProperty(value = "null")
    NUll(null);
   private final String value;
    AnsStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonCreator
    public String toString() {
        return value;
    }
}
