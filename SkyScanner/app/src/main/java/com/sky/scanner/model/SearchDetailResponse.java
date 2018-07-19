package com.sky.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SearchDetailResponse {

    private long SKYBenificiaryId ;
    private String TIN ="";
    private int DistrictId ;
    private int BlockId ;
    private int GPId ;
    private int VillageId ;
    private String Name ="";
    private String FathersName ="";
    private String Gender ="";
    private int GenderId ;
    private String DOB ="";
    private String Aadhaar ="";
    private String PinCode ="";
    private String HouseNo ="";
    private String Road ="";
    private String PDS ="";
    private String SmartCard ="";
    private String District ="";
    private String Block ="";
    private String GP ="";
    private String Village ="";
    private String AadhaarNo ="";
    private String AadhaarName ="";
    private String AFatherName ="";
    private String Address ="";
    private String CreatedBy ="";
    private List<AppSKYBenificiaryFamily> familyDet ;
    private List<AppSKYBenificiaryDocument> skybeneficiaryDocBObj;



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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFathersName() {
        return FathersName;
    }

    public void setFathersName(String fathersName) {
        FathersName = fathersName;
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAadhaar() {
        return Aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        Aadhaar = aadhaar;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getRoad() {
        return Road;
    }

    public void setRoad(String road) {
        Road = road;
    }

    public String getPDS() {
        return PDS;
    }

    public void setPDS(String PDS) {
        this.PDS = PDS;
    }

    public String getSmartCard() {
        return SmartCard;
    }

    public void setSmartCard(String smartCard) {
        SmartCard = smartCard;
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

    public String getAadhaarNo() {
        return AadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        AadhaarNo = aadhaarNo;
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

    public List<AppSKYBenificiaryFamily> getFamilyDet() {
        return familyDet;
    }

    public void setFamilyDet(List<AppSKYBenificiaryFamily> familyDet) {
        this.familyDet = familyDet;
    }

    public List<AppSKYBenificiaryDocument> getSkybeneficiaryDocBObj() {
        return skybeneficiaryDocBObj;
    }

    public void setSkybeneficiaryDocBObj(List<AppSKYBenificiaryDocument> skybeneficiaryDocBObj) {
        this.skybeneficiaryDocBObj = skybeneficiaryDocBObj;
    }

}
