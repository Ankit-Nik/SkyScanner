package com.sky.scanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FamilyDetModel_SkybeneficiaryFDocObj implements Parcelable{

    private long skyBenificiaryFamilyDocumentId ;
    private long skyBenificiaryFamilyId;
    private String name= "";
    private String description = "";
    private int type ;
    private String url = "";
    private String extImage = "";
    private int rowAttachmentType;
    private boolean isActive;
    private String createdBy = "";
    private boolean isApp;
    private String image = "";

    public FamilyDetModel_SkybeneficiaryFDocObj(Parcel in) {
        skyBenificiaryFamilyDocumentId = in.readLong();
        skyBenificiaryFamilyId = in.readLong();
        name = in.readString();
        description = in.readString();
        type = in.readInt();
        url = in.readString();
        extImage = in.readString();
        rowAttachmentType = in.readInt();
        isActive = in.readByte() != 0;
        createdBy = in.readString();
        isApp = in.readByte() != 0;
        image = in.readString();
    }

    public static final Creator<FamilyDetModel_SkybeneficiaryFDocObj> CREATOR = new Creator<FamilyDetModel_SkybeneficiaryFDocObj>() {
        @Override
        public FamilyDetModel_SkybeneficiaryFDocObj createFromParcel(Parcel in) {
            return new FamilyDetModel_SkybeneficiaryFDocObj(in);
        }

        @Override
        public FamilyDetModel_SkybeneficiaryFDocObj[] newArray(int size) {
            return new FamilyDetModel_SkybeneficiaryFDocObj[size];
        }
    };

    public FamilyDetModel_SkybeneficiaryFDocObj() {

    }

    public long getSkyBenificiaryFamilyDocumentId() {
        return skyBenificiaryFamilyDocumentId;
    }

    public void setSkyBenificiaryFamilyDocumentId(long skyBenificiaryFamilyDocumentId) {
        this.skyBenificiaryFamilyDocumentId = skyBenificiaryFamilyDocumentId;
    }

    public long getSkyBenificiaryFamilyId() {
        return skyBenificiaryFamilyId;
    }

    public void setSkyBenificiaryFamilyId(long skyBenificiaryFamilyId) {
        this.skyBenificiaryFamilyId = skyBenificiaryFamilyId;
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

    public int getRowAttachmentType() {
        return rowAttachmentType;
    }

    public void setRowAttachmentType(int rowAttachmentType) {
        this.rowAttachmentType = rowAttachmentType;
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
        parcel.writeLong(skyBenificiaryFamilyDocumentId);
        parcel.writeLong(skyBenificiaryFamilyId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(type);
        parcel.writeString(url);
        parcel.writeString(extImage);
        parcel.writeInt(rowAttachmentType);
        parcel.writeByte((byte) (isActive ? 1 : 0));
        parcel.writeString(createdBy);
        parcel.writeByte((byte) (isApp ? 1 : 0));
        parcel.writeString(image);
    }
}
