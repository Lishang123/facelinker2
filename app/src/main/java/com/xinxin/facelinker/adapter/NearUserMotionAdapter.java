package com.xinxin.facelinker.adapter;

import java.util.Date;

/**
 * Created by gengliang on 2015/7/26.
 */
public class NearUserMotionAdapter {
    private String account_num;
    private String nickname;
    private String motion;
    private Date date;

    public NearUserMotionAdapter(String account_num, String nickname, String motion, Date date) {
        this.account_num = account_num;
        this.nickname = nickname;
        this.motion = motion;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
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

    @Override
    public String toString() {
        return "账号：" + account_num + '\n' +
                "昵称：" + nickname + '\n' +
                "动态：" + motion +'\n'+
                "日期："+date.toString();
    }
}
