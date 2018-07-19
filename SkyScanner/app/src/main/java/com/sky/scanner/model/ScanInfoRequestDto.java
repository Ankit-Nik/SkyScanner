package com.sky.scanner.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by A0000350 on 5/24/2018.
 */

public class ScanInfoRequestDto {

    @SerializedName("LoggedInUserID")
    private String loggedInUserID;
    @SerializedName("AadharNumber")

    private String uid;
    @SerializedName("name")

    private String name;
    @SerializedName("Gender")

    private String gender;
    @SerializedName("yob")

    private String yob;

    @SerializedName("gname")
    private String gname;

    @SerializedName("co")

    private String co;
    @SerializedName("house")

    private String house;
    @SerializedName("street")

    private String street;
    @SerializedName("lm")

    private String lm;
    @SerializedName("loc")

    private String loc;
    @SerializedName("vtc")

    private String vtc;
    @SerializedName("po")

    private String po;
    @SerializedName("dist")

    private String dist;
    @SerializedName("subdist")

    private String subdist;
    @SerializedName("state")

    private String state;
    @SerializedName("PinCode")

    private String pc;
    @SerializedName("DOB")

    private String dob;
    @SerializedName("Phonenumber")

    private String phoneNo;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLm() {
        return lm;
    }

    public void setLm(String lm) {
        this.lm = lm;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getSubdist() {
        return subdist;
    }

    public void setSubdist(String subdist) {
        this.subdist = subdist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLoggedInUserID() {
        return loggedInUserID;
    }

    public void setLoggedInUserID(String loggedInUserID) {
        this.loggedInUserID = loggedInUserID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
}
