package com.xinxin.facelinker.domain;

import com.lidroid.xutils.db.annotation.NotNull;

/**
 * Created by xinxin on 2015/7/25.
 */
public class MyPals extends EntityBase {
    @NotNull
    String server_id;
    String isBlack;
    @NotNull
    String name;
    @NotNull
    String gender;
    String photourl;

    public MyPals() {
    }

    public MyPals(String server_id, String isBlack, String name, String gender, String photourl) {
        this.server_id = server_id;
        this.isBlack = isBlack;
        this.name = name;
        this.gender = gender;
        this.photourl = photourl;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    @Override
    public String toString() {
        return "MyPals{" +
                "server_id='" + server_id + '\'' +
                ", isBlack='" + isBlack + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", photourl='" + photourl + '\'' +
                '}';
    }
}
