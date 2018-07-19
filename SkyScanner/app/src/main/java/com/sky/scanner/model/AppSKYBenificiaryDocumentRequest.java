package com.sky.scanner.model;

public class AppSKYBenificiaryDocumentRequest {
    private long SKYBenificiaryDocumentId ;
    private long SKYBenificiaryId ;
    private String Name ="";
    private String Description ="";
    private int Type ;
    private String Url ="";
    private String extImage ="";
    private int RowAttachmentType ;
    private boolean isActive ;
    private String CreatedBy ="";
    // new coulmns
    private String Latitude ="";
    private String Longitude ="";
    private boolean isApp;
    private String image ="";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getSKYBenificiaryDocumentId() {
        return SKYBenificiaryDocumentId;
    }

    public void setSKYBenificiaryDocumentId(long SKYBenificiaryDocumentId) {
        this.SKYBenificiaryDocumentId = SKYBenificiaryDocumentId;
    }

    public long getSKYBenificiaryId() {
        return SKYBenificiaryId;
    }

    public void setSKYBenificiaryId(long SKYBenificiaryId) {
        this.SKYBenificiaryId = SKYBenificiaryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getExtImage() {
        return extImage;
    }

    public void setExtImage(String extImage) {
        this.extImage = extImage;
    }

    public int getRowAttachmentType() {
        return RowAttachmentType;
    }

    public void setRowAttachmentType(int rowAttachmentType) {
        RowAttachmentType = rowAttachmentType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

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

    public boolean isApp() {
        return isApp;
    }

    public void setApp(boolean app) {
        isApp = app;
    }
}

