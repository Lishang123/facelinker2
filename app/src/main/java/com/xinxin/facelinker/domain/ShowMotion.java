package com.xinxin.facelinker.domain;

import java.util.Arrays;

/**
 * Created by gengliang on 2015/7/25.
 */
public class ShowMotion {
    private String other_account_num;
    private String nickname;
    private String motion;
    private String[] comment;

    @Override
    public String toString() {
        return "ShowMotion{" +
                "other_account_num='" + other_account_num + '\'' +
                ", nickname='" + nickname + '\'' +
                ", motion='" + motion + '\'' +
                ", comment=" + Arrays.toString(comment) +
                '}';
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

    public String[] getComment() {
        return comment;
    }

    public void setComment(String[] comment) {
        this.comment = comment;
    }

    public ShowMotion(String other_account_num, String nickname, String motion, String[] comment) {

        this.other_account_num = other_account_num;
        this.nickname = nickname;
        this.motion = motion;
        this.comment = comment;
    }
}
