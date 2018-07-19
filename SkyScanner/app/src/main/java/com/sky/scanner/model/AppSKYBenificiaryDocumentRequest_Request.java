package com.sky.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AppSKYBenificiaryDocumentRequest_Request implements Parcelable{
    private long skyBenificiaryDocumentId ;
    private long skyBenificiaryId ;
    private String name ="";
    private String description ="";
    private int type ;
    private String url ="";
    private String extImage ="";
    private boolean isActive ;
    private String createdBy ="";
    private String latitude ="";
    private String longitude ="";
    private boolean isApp;
    private String image ="";

    public AppSKYBenificiaryDocumentRequest_Request(Parcel in) {
        skyBenificiaryDocumentId = in.readLong();
        skyBenificiaryId = in.readLong();
        name = in.readString();
        description = in.readString();
        type = in.readInt();
        url = in.readString();
        extImage = in.readString();
        isActive = in.readByte() != 0;
        createdBy = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        isApp = in.readByte() != 0;
        image = in.readString();
    }

    public static final Creator<AppSKYBenificiaryDocumentRequest_Request> CREATOR = new Creator<AppSKYBenificiaryDocumentRequest_Request>() {
        @Override
        public AppSKYBenificiaryDocumentRequest_Request createFromParcel(Parcel in) {
            return new AppSKYBenificiaryDocumentRequest_Request(in);
        }

        @Override
        public AppSKYBenificiaryDocumentRequest_Request[] newArray(int size) {
            return new AppSKYBenificiaryDocumentRequest_Request[size];
        }
    };

    public AppSKYBenificiaryDocumentRequest_Request() {

    }

    public long getSkyBenificiaryDocumentId() {
        return skyBenificiaryDocumentId;
    }

    public void setSkyBenificiaryDocumentId(long skyBenificiaryDocumentId) {
        this.skyBenificiaryDocumentId = skyBenificiaryDocumentId;
    }

    public long getSkyBenificiaryId() {
        return skyBenificiaryId;
    }

    public void setSkyBenificiaryId(long skyBenificiaryId) {
        this.skyBenificiaryId = skyBenificiaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtImage() {
        return extImage;
    }

    public void setExtImage(String extImage) {
        this.extImage = extImage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isApp() {
        return isApp;
    }

    public void setApp(boolean app) {
        isApp = app;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(skyBenificiaryDocumentId);
        parcel.writeLong(skyBenificiaryId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(type);
        parcel.writeString(url);
        parcel.writeString(extImage);
        parcel.writeByte((byte) (isActive ? 1 : 0));
        parcel.writeString(createdBy);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeByte((byte) (isApp ? 1 : 0));
        parcel.writeString(image);
    }
}


