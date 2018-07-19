package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

public class SearchDetailRequest {

    @SerializedName("Id")
    private String Id ;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
