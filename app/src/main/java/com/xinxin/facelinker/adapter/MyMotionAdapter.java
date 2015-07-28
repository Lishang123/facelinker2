package com.xinxin.facelinker.adapter;

import java.util.Date;

/**
 * Created by gengliang on 2015/7/25.
 */
public class MyMotionAdapter {

    private String motion;
    private String comment;
    private Date date;

    public MyMotionAdapter(String motion, String comment,Date date) {
        this.date=date;
        this.motion = motion;
        this.comment = comment;
    }

    public String getMotion() {
        return motion;
    }

    public String getComment() {
        return comment;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "动态:" + motion + '\n' +
                "评论:" + comment + '\n'+
                "日期："+date.toString();
    }

}
