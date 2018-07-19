package com.sky.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FamilyDetModel implements Parcelable{

    private long skyBenificiaryfamilyId ;
    private String tin = "";
    private String apppds = "";
    private String appSmartCard ="";
    private String appmanrega = "";
    private String appmanregA1 ="";
    private boolean isApp  ;
    private int isdisable;
    private long GenderId;
    private int YOB;
    private String Aadhaar = "";
    private String AadhaarName ="";
    private String GuardianName  = "";
    private String Address  = "";
    private List<FamilyDetModel_SkybeneficiaryFDocObj> skybeneficiaryFDocObj;



    public FamilyDetModel(Parcel in) {
        skyBenificiaryfamilyId = in.readLong();
        tin = in.readString();
        apppds = in.readString();
        appSmartCard = in.readString();
        appmanrega = in.readString();
        appmanregA1 = in.readString();
        isApp = in.readByte() != 0;
        isdisable = in.readInt();
        GenderId = in.readLong();
        YOB = in.readInt();
        Aadhaar = in.readString();
        AadhaarName = in.readString();
        GuardianName = in.readString();
        Address = in.readString();

        skybeneficiaryFDocObj = in.createTypedArrayList(FamilyDetModel_SkybeneficiaryFDocObj.CREATOR);
    }

    public static final Creator<FamilyDetModel> CREATOR = new Creator<FamilyDetModel>() {
        @Override
        public FamilyDetModel createFromParcel(Parcel in) {
            return new FamilyDetModel(in);
        }

        @Override
        public FamilyDetModel[] newArray(int size) {
            return new FamilyDetModel[size];
        }
    };

    public FamilyDetModel() {

    }

    public long getSkyBenificiaryfamilyId() {
        return skyBenificiaryfamilyId;
    }

    public void setSkyBenificiaryfamilyId(long skyBenificiaryfamilyId) {
        this.skyBenificiaryfamilyId = skyBenificiaryfamilyId;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getApppds() {
        return apppds;
    }

    public void setApppds(String apppds) {
        this.apppds = apppds;
    }

    public String getAppSmartCard() {
        return appSmartCard;
    }

    public void setAppSmartCard(String appSmartCard) {
        this.appSmartCard = appSmartCard;
    }

    public String getAppmanrega() {
        return appmanrega;
    }

    public void setAppmanrega(String appmanrega) {
        this.appmanrega = appmanrega;
    }

    public String getAppmanregA1() {
        return appmanregA1;
    }

    public void setAppmanregA1(String appmanregA1) {
        this.appmanregA1 = appmanregA1;
    }

    public boolean isApp() {
        return isApp;
    }

    public void setApp(boolean app) {
        isApp = app;
    }

    public int getIsdisable() {
        return isdisable;
    }

    public void setIsdisable(int isdisable) {
        this.isdisable = isdisable;
    }

    public long getGenderId() {
        return GenderId;
    }

    public void setGenderId(long genderId) {
        GenderId = genderId;
    }

    public int getYOB() {
        return YOB;
    }

    public void setYOB(int YOB) {
        this.YOB = YOB;
    }

    public String getAadhaar() {
        return Aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        Aadhaar = aadhaar;
    }

    public String getAadhaarName() {
        return AadhaarName;
    }

    public void setAadhaarName(String aadhaarName) {
        AadhaarName = aadhaarName;
    }

    public String getGuardianName() {
        return GuardianName;
    }

    public void setGuardianName(String guardianName) {
        GuardianName = guardianName;
    }

    public List<FamilyDetModel_SkybeneficiaryFDocObj> getSkybeneficiaryFDocObj() {
        return skybeneficiaryFDocObj;
    }

    public void setSkybeneficiaryFDocObj(List<FamilyDetModel_SkybeneficiaryFDocObj> skybeneficiaryFDocObj) {
        this.skybeneficiaryFDocObj = skybeneficiaryFDocObj;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(skyBenificiaryfamilyId);
        parcel.writeString(tin);
        parcel.writeString(apppds);
        parcel.writeString(appSmartCard);
        parcel.writeString(appmanrega);
        parcel.writeString(appmanregA1);
        parcel.writeByte((byte) (isApp ? 1 : 0));
        parcel.writeInt(isdisable);
        parcel.writeLong(GenderId);
        parcel.writeInt(YOB);
        parcel.writeString(Aadhaar);
        parcel.writeString(AadhaarName);
        parcel.writeString(GuardianName);
        parcel.writeString(Address);
        parcel.writeTypedList(skybeneficiaryFDocObj);
    }
}
