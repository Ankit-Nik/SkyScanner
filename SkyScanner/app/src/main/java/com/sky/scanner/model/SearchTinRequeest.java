package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchTinRequeest {

    @SerializedName("start")
    private String start="";

    @SerializedName("length")
    private String length ="";

    @SerializedName("filters")
    private ArrayList<FilterModel> filters ;


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public ArrayList<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(ArrayList<FilterModel> filters) {
        this.filters = filters;
    }
}
