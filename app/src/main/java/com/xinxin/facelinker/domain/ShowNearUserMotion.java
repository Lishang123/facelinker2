package com.xinxin.facelinker.domain;

import java.util.Date;

/**
 * Created by gengliang on 2015/7/26.
 */
public class ShowNearUserMotion {
    private String other_account_num;
    private String nickname;
    private String motion;
    private Date date;
    private String motion_id;

    @Override
    public String toString() {
        return "ShowNearUserMotion{" +
                "other_account_num='" + other_account_num + '\'' +
                ", nickname='" + nickname + '\'' +
                ", motion='" + motion + '\'' +
                ", date=" + date +
                ", motion_id='" + motion_id + '\'' +
                '}';
    }

    public String getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(String motion_id) {
        this.motion_id = motion_id;
    }

    public ShowNearUserMotion(String other_account_num, String nickname, String motion, Date date, String motion_id) {

        this.other_account_num = other_account_num;
        this.nickname = nickname;
        this.motion = motion;
        this.date = date;
        this.motion_id = motion_id;
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

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
