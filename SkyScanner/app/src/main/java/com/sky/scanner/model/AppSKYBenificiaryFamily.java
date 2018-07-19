package com.sky.scanner.model;

public class AppSKYBenificiaryFamily {

    private String TIN ="";
    private String Name ="";
    private String Gender ="";
    private int GenderId ;
    private String Aadhaar ="";
    private String CreatedBy ="";
    private int YOB;
    private String familyStatus ="";
    private int SKYBenificiaryfamilyId;

    public long getSKYBenificiaryfamilyId() {
        return SKYBenificiaryfamilyId;
    }

    public void setSKYBenificiaryfamilyId(int SKYBenificiaryfamilyId) {
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
}
