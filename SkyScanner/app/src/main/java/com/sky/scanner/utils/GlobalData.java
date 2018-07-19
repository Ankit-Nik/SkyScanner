package com.sky.scanner.utils;

import android.app.Application;

import com.sky.scanner.ModelsNew.SubmitDataNewModel;
import com.sky.scanner.model.FamilyDetModel;
import com.sky.scanner.model.SearchTinResponse;

import java.util.ArrayList;

public class GlobalData  {

    private SubmitDataNewModel familyDetarray;

    private static GlobalData instance;

    // Global variable
    private int data;
    private String userName;
    private SearchTinResponse response;

    public SearchTinResponse getresponse() {
        return response;
    }

    public void setresponse(SearchTinResponse response) {
        this.response = response;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Restrict the constructor from being instantiated
    private GlobalData(){}

    public void setData(int d){
        this.data=d;
    }
    public int getData(){
        return this.data;
    }

    public static synchronized GlobalData getInstance(){
        if(instance==null){
            instance=new GlobalData();
        }
        return instance;
    }

    public SubmitDataNewModel getFamilyDetarray() {
        return familyDetarray;
    }

    public void setFamilyDetarray(SubmitDataNewModel familyDetarray) {
        this.familyDetarray = familyDetarray;
    }
}
