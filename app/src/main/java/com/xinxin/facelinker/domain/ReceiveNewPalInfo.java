package com.xinxin.facelinker.domain;

/**
 * Created by gengliang on 2015/7/25.
 */
public class ReceiveNewPalInfo {
    private String other_account_num;
    private String nickname;
    private String message;

    @Override
    public String toString() {
        return "ReceiveNewPalInfo{" +
                "other_account_num='" + other_account_num + '\'' +
                ", nickname='" + nickname + '\'' +
                ", message='" + message + '\'' +
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReceiveNewPalInfo(String other_account_num, String nickname, String message) {

        this.other_account_num = other_account_num;
        this.nickname = nickname;
        this.message = message;
    }
}
