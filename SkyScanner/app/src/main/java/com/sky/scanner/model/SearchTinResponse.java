package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchTinResponse {

    @SerializedName("data")
    private ArrayList<SearchTinResponseModel> data;

    public ArrayList<SearchTinResponseModel> getResults() {
        return data;
    }

    public void setResults(ArrayList<SearchTinResponseModel> results) {
        this.data = results;
    }

}
