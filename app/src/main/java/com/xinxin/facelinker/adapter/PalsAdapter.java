package com.xinxin.facelinker.adapter;

/**
 * Created by gengliang on 2015/7/26.
 */
public class PalsAdapter {
    private String motion;
    private String nickname;
    private String comment;

    public PalsAdapter(String motion, String nickname, String comment) {
        this.motion = motion;
        this.nickname = nickname;
        this.comment = comment;
    }

    public String getMotion() {
        return motion;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {

        return nickname;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {

        return comment;
    }

    @Override
    public String toString() {
        return "昵称：" + nickname + '\n' +
                "动态：" + motion+'\n'+
                "评论："+comment;
    }
}
