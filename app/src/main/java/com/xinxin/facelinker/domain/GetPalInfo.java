package com.xinxin.facelinker.domain;

/**
 * Created by gengliang on 2015/7/24.
 */
public class GetPalInfo {

    private String photo_url;
    private String other_account_num;
    private String nickname;
    private String gender;
    private String birthday;
    private String mood;
    private String honour;

    public GetPalInfo(String photo_url, String other_account_num, String nickname, String gender, String birthday, String mood, String honour) {
        this.photo_url = photo_url;
        this.other_account_num = other_account_num;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.mood = mood;
        this.honour = honour;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getOther_account_num() {
        return other_account_num;
    }

    public void setOther_account_num(String other_account_num) {
        this.other_account_num = other_account_num;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getHonour() {
        return honour;
    }

    public void setHonour(String honour) {
        this.honour = honour;
    }

    @Override
    public String toString() {
        return "GetPalInfo{" +
                "photo_url='" + photo_url + '\'' +
                ", other_account_num='" + other_account_num + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", mood='" + mood + '\'' +
                ", honour='" + honour + '\'' +
                '}';
    }
}
