package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

public class SearchVillage {
    @SerializedName("text")
    String text;

    @SerializedName("value")
    String value;


    public SearchVillage(String text,String value ) {
        this.text = text;
        this.value = value;

    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
