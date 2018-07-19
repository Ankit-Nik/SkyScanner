package com.sky.scanner.ModelsNew;

public class LDocNewModel {

    String MasterBenificiaryId;
    String DocName;
    String Url;
    String Extension;
    String IsActive;
    String image;
    String Latitude;
    String Longitude;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getMasterBenificiaryId() {
        return MasterBenificiaryId;
    }

    public void setMasterBenificiaryId(String masterBenificiaryId) {
        MasterBenificiaryId = masterBenificiaryId;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
