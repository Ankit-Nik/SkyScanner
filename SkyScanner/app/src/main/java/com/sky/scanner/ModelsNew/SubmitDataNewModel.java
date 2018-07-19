package com.sky.scanner.ModelsNew;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SubmitDataNewModel implements Parcelable {

    String  TIN ="";
    long MasterBenificiaryId;
    String UniqueTin="";
    String HH_ID="";
    String CreatedBy="";
    String ErrorMessage="";
    String VName="";
    String GPName="";
    String BName="";
    String DName="";



    ArrayList<FamilyDetNewModel> familyDet;
    ArrayList<LDocNewModel> ldoc;

    public SubmitDataNewModel(Parcel in) {
        TIN = in.readString();
        MasterBenificiaryId = in.readLong();
        UniqueTin = in.readString();
        HH_ID = in.readString();
        CreatedBy = in.readString();
        ErrorMessage = in.readString();
        VName = in.readString();
        GPName = in.readString();
        BName = in.readString();
        DName = in.readString();
    }

    public static final Creator<SubmitDataNewModel> CREATOR = new Creator<SubmitDataNewModel>() {
        @Override
        public SubmitDataNewModel createFromParcel(Parcel in) {
            return new SubmitDataNewModel(in);
        }

        @Override
        public SubmitDataNewModel[] newArray(int size) {
            return new SubmitDataNewModel[size];
        }
    };

    public SubmitDataNewModel() {

    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public long getMasterBenificiaryId() {
        return MasterBenificiaryId;
    }

    public void setMasterBenificiaryId(long masterBenificiaryId) {
        MasterBenificiaryId = masterBenificiaryId;
    }

    public String getUniqueTin() {
        return UniqueTin;
    }

    public void setUniqueTin(String uniqueTin) {
        UniqueTin = uniqueTin;
    }

    public String getHH_ID() {
        return HH_ID;
    }

    public void setHH_ID(String HH_ID) {
        this.HH_ID = HH_ID;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getVName() {
        return VName;
    }

    public void setVName(String VName) {
        this.VName = VName;
    }

    public String getGPName() {
        return GPName;
    }

    public void setGPName(String GPName) {
        this.GPName = GPName;
    }

    public String getBName() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public ArrayList<FamilyDetNewModel> getFamilyDet() {
        return familyDet;
    }

    public void setFamilyDet(ArrayList<FamilyDetNewModel> familyDet) {
        this.familyDet = familyDet;
    }

    public ArrayList<LDocNewModel> getLdoc() {
        return ldoc;
    }

    public void setLdoc(ArrayList<LDocNewModel> ldoc) {
        this.ldoc = ldoc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(TIN);
        parcel.writeLong(MasterBenificiaryId);
        parcel.writeString(UniqueTin);
        parcel.writeString(HH_ID);
        parcel.writeString(CreatedBy);
        parcel.writeString(ErrorMessage);
        parcel.writeString(VName);
        parcel.writeString(GPName);
        parcel.writeString(BName);
        parcel.writeString(DName);
    }
}
