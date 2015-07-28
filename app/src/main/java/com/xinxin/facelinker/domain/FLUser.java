package com.xinxin.facelinker.domain;

import com.lidroid.xutils.db.annotation.NotNull;

/**
 * Created by xinxin on 2015/7/25.
 */
public class FLUser extends EntityBase {
    @NotNull
    String account;
    @NotNull
    String name;
    @NotNull
    String server_id;
    String photourl;
    String gender;
    String age;
    String location;
    String honour;
    String lasttime;
    String phone;
    String email;
    String job;
    String mood;

    @Override
    public String toString() {
        return "FLUser{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", server_id='" + server_id + '\'' +
                ", photourl='" + photourl + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", location='" + location + '\'' +
                ", honour='" + honour + '\'' +
                ", lasttime='" + lasttime + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", job='" + job + '\'' +
                ", mood='" + mood + '\'' +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHonour() {
        return honour;
    }

    public void setHonour(String honour) {
        this.honour = honour;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public FLUser(String account, String name, String server_id, String photourl, String gender, String age, String location, String honour, String lasttime, String phone, String email, String job, String mood) {
        this.account = account;
        this.name = name;
        this.server_id = server_id;
        this.photourl = photourl;
        this.gender = gender;
        this.age = age;
        this.location = location;
        this.honour = honour;
        this.lasttime = lasttime;
        this.phone = phone;
        this.email = email;
        this.job = job;
        this.mood = mood;
    }

    public FLUser() {
    }


}
