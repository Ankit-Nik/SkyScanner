package com.sky.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Submit_All_Data_Request implements Parcelable {
    private long SKYBenificiaryId ;
    private String TIN ="";
    private int DistrictId ;
    private int BlockId ;
    private int GPId ;
    private int VillageId ;
    private int GenderId ;
    private int YOB ;
    private String Aadhaar ="";
    private String AadhaarName ="";
    private String AFatherName ="";
    private String Address ="";
    private String CreatedBy ="";
    private String apppds = "";
    private String appSmartCard= "";
    private String appmanrega= "";
    private String appmanregA1= "";
    private int isdisable;

    public int getIsdisable() {
        return isdisable;
    }

    public void setIsdisable(int isdisable) {
        this.isdisable = isdisable;
    }

    private ArrayList<AppSKYBenificiaryDocumentRequest_Request> skybeneficiaryDocBObj;
    private ArrayList<FamilyDetModel> familyDet;

    public Submit_All_Data_Request(Parcel in) {
        SKYBenificiaryId = in.readLong();
        TIN = in.readString();
        isdisable = in.readInt();
        DistrictId = in.readInt();
        BlockId = in.readInt();
        GPId = in.readInt();
        VillageId = in.readInt();
        GenderId = in.readInt();
        YOB = in.readInt();
        Aadhaar = in.readString();
        AadhaarName = in.readString();
        AFatherName = in.readString();
        Address = in.readString();
        CreatedBy = in.readString();
        apppds = in.readString();
        appSmartCard = in.readString();
        appmanrega = in.readString();
        appmanregA1 = in.readString();
    }

    public static final Creator<Submit_All_Data_Request> CREATOR = new Creator<Submit_All_Data_Request>() {
        @Override
        public Submit_All_Data_Request createFromParcel(Parcel in) {
            return new Submit_All_Data_Request(in);
        }

        @Override
        public Submit_All_Data_Request[] newArray(int size) {
            return new Submit_All_Data_Request[size];
        }
    };

    public Submit_All_Data_Request() {

    }

    public long getSKYBenificiaryId() {
        return SKYBenificiaryId;
    }

    public void setSKYBenificiaryId(long SKYBenificiaryId) {
        this.SKYBenificiaryId = SKYBenificiaryId;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public int getBlockId() {
        return BlockId;
    }

    public void setBlockId(int blockId) {
        BlockId = blockId;
    }

    public int getGPId() {
        return GPId;
    }

    public void setGPId(int GPId) {
        this.GPId = GPId;
    }

    public int getVillageId() {
        return VillageId;
    }

    public void setVillageId(int villageId) {
        VillageId = villageId;
    }

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
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

    public String getAFatherName() {
        return AFatherName;
    }

    public void setAFatherName(String AFatherName) {
        this.AFatherName = AFatherName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
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

    public List<AppSKYBenificiaryDocumentRequest_Request> getSkybeneficiaryDocBObj() {
        return skybeneficiaryDocBObj;
    }

    public void setSkybeneficiaryDocBObj(ArrayList<AppSKYBenificiaryDocumentRequest_Request> skybeneficiaryDocBObj) {
        this.skybeneficiaryDocBObj = skybeneficiaryDocBObj;
    }

    public List<FamilyDetModel> getFamilyDet() {
        return familyDet;
    }

    public void setFamilyDet(ArrayList<FamilyDetModel> familyDet) {
        this.familyDet = familyDet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(SKYBenificiaryId);
        parcel.writeString(TIN);
        parcel.writeInt(isdisable);
        parcel.writeInt(DistrictId);
        parcel.writeInt(BlockId);
        parcel.writeInt(GPId);
        parcel.writeInt(VillageId);
        parcel.writeInt(GenderId);
        parcel.writeInt(YOB);
        parcel.writeString(Aadhaar);
        parcel.writeString(AadhaarName);
        parcel.writeString(AFatherName);
        parcel.writeString(Address);
        parcel.writeString(CreatedBy);
        parcel.writeString(apppds);
        parcel.writeString(appSmartCard);
        parcel.writeString(appmanrega);
        parcel.writeString(appmanregA1);
    }
}
