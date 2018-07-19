package com.sky.scanner.ModelsNew;

import java.util.ArrayList;

public class UserDetailsModel {

    String  TIN = "";
    String  UniqueTIN= "";
    String  HH_ID= "";
    String  CreatedBy= "";
    String  ErrorMessage= "";
    String  VName= "";
    String  GPName= "";
    String  BName= "";
    String  DName= "";
    long MasterBenificiaryId;
    ArrayList<FamilyDet> familyDet;
    ArrayList<LGender> lgender;
    ArrayList<FamilyStatus> lfamilystatus;
    ArrayList<LRelation> lrelation;

    public long getMasterBenificiaryId() {
        return MasterBenificiaryId;
    }

    public void setMasterBenificiaryId(long masterBenificiaryId) {
        MasterBenificiaryId = masterBenificiaryId;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getUniqueTIN() {
        return UniqueTIN;
    }

    public void setUniqueTIN(String UniqueTIN) {
        this.UniqueTIN = UniqueTIN;
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

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
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

    public ArrayList<FamilyDet> getFamilyDet() {
        return familyDet;
    }

    public void setFamilyDet(ArrayList<FamilyDet> familyDet) {
        this.familyDet = familyDet;
    }

    public ArrayList<LGender> getLgender() {
        return lgender;
    }

    public void setLgender(ArrayList<LGender> lgender) {
        this.lgender = lgender;
    }

    public ArrayList<FamilyStatus> getLfamilystatus() {
        return lfamilystatus;
    }

    public void setLfamilystatus(ArrayList<FamilyStatus> lfamilystatus) {
        this.lfamilystatus = lfamilystatus;
    }

    public ArrayList<LRelation> getLrelation() {
        return lrelation;
    }

    public void setLrelation(ArrayList<LRelation> lrelation) {
        this.lrelation = lrelation;
    }
}
