package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

public class FilterModel {


    @SerializedName("name")
    private String name="";

    @SerializedName("value")
    private String value="";
    //  "name": "VillageId",
    //         "value": 5


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
