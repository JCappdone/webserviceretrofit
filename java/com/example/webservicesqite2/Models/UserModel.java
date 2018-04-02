package com.example.webservicesqite2.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shriji on 30/3/18.
 */

public class UserModel implements Parcelable {

    private int id;
    private String name;
    private String phone;
    private String image;
    private String status;

    public UserModel(String name, String phone, String image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public UserModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.image);
        dest.writeString(this.status);
    }

    protected UserModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.phone = in.readString();
        this.image = in.readString();
        this.status = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
