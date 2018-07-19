package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchVillageResponseDto {

    @SerializedName("data")
    private List<SearchVillage> data;

    public List<SearchVillage> getResults() {
        return data;
    }

    public void setResults(List<SearchVillage> results) {
        this.data = results;
    }
}
