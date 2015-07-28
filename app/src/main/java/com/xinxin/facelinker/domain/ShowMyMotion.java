package com.xinxin.facelinker.domain;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by gengliang on 2015/7/26.
 */
public class ShowMyMotion {
    private String motion;
    private String[] comment;
    private Date date;
    private String motion_id;

    public ShowMyMotion(String motion, String[] comment, Date date, String motion_id) {

        this.motion = motion;
        this.comment = comment;
        this.date = date;
        this.motion_id = motion_id;
    }

    @Override
    public String toString() {
        return "ShowMyMotion{" +
                "motion='" + motion + '\'' +
                ", comment=" + Arrays.toString(comment) +
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
