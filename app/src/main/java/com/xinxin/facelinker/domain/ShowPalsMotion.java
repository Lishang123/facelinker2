package com.xinxin.facelinker.domain;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by gengliang on 2015/7/26.
 */
public class ShowPalsMotion {
    private String motion;
    private String[] comment;
    private String motion_id;
    private Date date;
    private String nickname;

    public ShowPalsMotion(String motion, String[] comment, String motion_id, Date date, String nickname) {
        this.motion = motion;
        this.comment = comment;
        this.motion_id = motion_id;
        this.date = date;
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

    public String getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(String motion_id) {
        this.motion_id = motion_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ShowPalsMotion{" +
                "motion='" + motion + '\'' +
                ", comment=" + Arrays.toString(comment) +
                ", motion_id='" + motion_id + '\'' +
                ", date=" + date +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
