package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;
import com.sky.scanner.ModelsNew.UserDetailsModel;

import java.util.List;

public class SearchDetailResponseModel {

    @SerializedName("data")
    private UserDetailsModel data;

    public  UserDetailsModel  getResults() {
        return data;
    }

    public void setResults( UserDetailsModel results) {
        this.data = results;
    }
}