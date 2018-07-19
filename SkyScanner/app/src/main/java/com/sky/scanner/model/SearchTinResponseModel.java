package com.sky.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchTinResponseModel implements Parcelable{


    private String TIN ="";
    private String HHNumber ="";
    private int StateCode =0;
    private String District ="";
    private String Block="";
    private String GP="";
    private String Village="";
    private String ULB ="";
    private String HouseholdNumber ="";
    private String HouseholdMemberNumber="";
    private String Name ="";
    private String FatherName ="";
    private String MotherName ="";
    private String Gender ="";
    private String Relation="";
    private String YOB ="";
    private int MaritalStatusId =0;
    private int OccupationId=0;
    private String BeneficiairyOne ="";
    private String BeneficiairyTwo="";
    private String EditBtn ="";
    private String IsBeneficiary ="";
    private boolean IsIdentified =false;
    private long SKYBenificiaryId =0;
    private int isValidate =0;

    protected SearchTinResponseModel(Parcel in) {
        TIN = in.readString();
        HHNumber = in.readString();
        StateCode = in.readInt();
        District = in.readString();
        Block = in.readString();
        GP = in.readString();
        Village = in.readString();
        ULB = in.readString();
        HouseholdNumber = in.readString();
        HouseholdMemberNumber = in.readString();
        Name = in.readString();
        FatherName = in.readString();
        MotherName = in.readString();
        Gender = in.readString();
        Relation = in.readString();
        YOB = in.readString();
        MaritalStatusId = in.readInt();
        OccupationId = in.readInt();
        BeneficiairyOne = in.readString();
        BeneficiairyTwo = in.readString();
        EditBtn = in.readString();
        IsBeneficiary = in.readString();
        IsIdentified = in.readByte() != 0;
        SKYBenificiaryId = in.readLong();
        isValidate = in.readInt();
    }

    public static final Creator<SearchTinResponseModel> CREATOR = new Creator<SearchTinResponseModel>() {
        @Override
        public SearchTinResponseModel createFromParcel(Parcel in) {
            return new SearchTinResponseModel(in);
        }

        @Override
        public SearchTinResponseModel[] newArray(int size) {
            return new SearchTinResponseModel[size];
        }
    };

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getHHNumber() {
        return HHNumber;
    }

    public void setHHNumber(String HHNumber) {
        this.HHNumber = HHNumber;
    }

    public int getStateCode() {
        return StateCode;
    }

    public void setStateCode(int stateCode) {
        StateCode = stateCode;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getGP() {
        return GP;
    }

    public void setGP(String GP) {
        this.GP = GP;
    }

    public String getVillage() {
        return Village;
    }
    public void setVillage(String village) {
        Village = village;
    }

    public String getULB() {
        return ULB;
    }

    public void setULB(String ULB) {
        this.ULB = ULB;
    }

    public String getHouseholdNumber() {
        return HouseholdNumber;
    }

    public void setHouseholdNumber(String householdNumber) {
        HouseholdNumber = householdNumber;
    }

    public String getHouseholdMemberNumber() {
        return HouseholdMemberNumber;
    }

    public void setHouseholdMemberNumber(String householdMemberNumber) {
        HouseholdMemberNumber = householdMemberNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getYOB() {
        return YOB;
    }

    public void setYOB(String YOB) {
        this.YOB = YOB;
    }

    public int getMaritalStatusId() {
        return MaritalStatusId;
    }

    public void setMaritalStatusId(int maritalStatusId) {
        MaritalStatusId = maritalStatusId;
    }

    public int getOccupationId() {
        return OccupationId;
    }

    public void setOccupationId(int occupationId) {
        OccupationId = occupationId;
    }

    public String getBeneficiairyOne() {
        return BeneficiairyOne;
    }

    public void setBeneficiairyOne(String beneficiairyOne) {
        BeneficiairyOne = beneficiairyOne;
    }

    public String getBeneficiairyTwo() {
        return BeneficiairyTwo;
    }

    public void setBeneficiairyTwo(String beneficiairyTwo) {
        BeneficiairyTwo = beneficiairyTwo;
    }

    public String getEditBtn() {
        return EditBtn;
    }

    public void setEditBtn(String editBtn) {
        EditBtn = editBtn;
    }

    public String getIsBeneficiary() {
        return IsBeneficiary;
    }

    public void setIsBeneficiary(String isBeneficiary) {
        IsBeneficiary = isBeneficiary;
    }

    public boolean isIdentified() {
        return IsIdentified;
    }

    public void setIdentified(boolean identified) {
        IsIdentified = identified;
    }

    public long getSKYBenificiaryId() {
        return SKYBenificiaryId;
    }

    public void setSKYBenificiaryId(long SKYBenificiaryId) {
        this.SKYBenificiaryId = SKYBenificiaryId;
    }

    public int getIsValidate() {
        return isValidate;
    }

    public void setIsValidate(int isValidate) {
        this.isValidate = isValidate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(TIN);
        parcel.writeString(HHNumber);
        parcel.writeInt(StateCode);
        parcel.writeString(District);
        parcel.writeString(Block);
        parcel.writeString(GP);
        parcel.writeString(Village);
        parcel.writeString(ULB);
        parcel.writeString(HouseholdNumber);
        parcel.writeString(HouseholdMemberNumber);
        parcel.writeString(Name);
        parcel.writeString(FatherName);
        parcel.writeString(MotherName);
        parcel.writeString(Gender);
        parcel.writeString(Relation);
        parcel.writeString(YOB);
        parcel.writeInt(MaritalStatusId);
        parcel.writeInt(OccupationId);
        parcel.writeString(BeneficiairyOne);
        parcel.writeString(BeneficiairyTwo);
        parcel.writeString(EditBtn);
        parcel.writeString(IsBeneficiary);
        parcel.writeByte((byte) (IsIdentified ? 1 : 0));
        parcel.writeLong(SKYBenificiaryId);
        parcel.writeInt(isValidate);
    }
}
