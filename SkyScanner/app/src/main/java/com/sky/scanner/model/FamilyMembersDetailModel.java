package com.sky.scanner.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FamilyMembersDetailModel  extends ArrayList<Parcelable> implements Parcelable{

    private String TIN ="";
    private String Name ="";
    private String Gender ="";
    private int GenderId ;
    private String Aadhaar ="";
    private String CreatedBy ="";
    private int YOB;
    private String familyStatus ="";
    private long SKYBenificiaryfamilyId;


    public FamilyMembersDetailModel(Parcel in) {
        TIN = in.readString();
        Name = in.readString();
        Gender = in.readString();
        GenderId = in.readInt();
        Aadhaar = in.readString();
        CreatedBy = in.readString();
        YOB = in.readInt();
        familyStatus = in.readString();
        SKYBenificiaryfamilyId = in.readLong();
    }

    public static final Creator<FamilyMembersDetailModel> CREATOR = new Creator<FamilyMembersDetailModel>() {
        @Override
        public FamilyMembersDetailModel createFromParcel(Parcel in) {
            return new FamilyMembersDetailModel(in);
        }

        @Override
        public FamilyMembersDetailModel[] newArray(int size) {
            return new FamilyMembersDetailModel[size];
        }
    };

    public FamilyMembersDetailModel() {

    }

    public long getSKYBenificiaryfamilyId() {
        return SKYBenificiaryfamilyId;
    }

    public void setSKYBenificiaryfamilyId(long SKYBenificiaryfamilyId) {
        this.SKYBenificiaryfamilyId = SKYBenificiaryfamilyId;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }

    public String getAadhaar() {
        return Aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        Aadhaar = aadhaar;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public int getYOB() {
        return YOB;
    }

    public void setYOB(int YOB) {
        this.YOB = YOB;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(TIN);
        parcel.writeString(Name);
        parcel.writeString(Gender);
        parcel.writeInt(GenderId);
        parcel.writeString(Aadhaar);
        parcel.writeString(CreatedBy);
        parcel.writeInt(YOB);
        parcel.writeString(familyStatus);
        parcel.writeLong(SKYBenificiaryfamilyId);
    }
}
