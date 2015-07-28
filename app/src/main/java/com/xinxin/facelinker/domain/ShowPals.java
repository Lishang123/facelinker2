package com.xinxin.facelinker.domain;

/**
 * Created by gengliang on 2015/7/26.
 */
public class ShowPals {
    private String nickname;
    private String other_account_num;

    @Override
    public String toString() {
        return "ShowPals{" +
                "nickname='" + nickname + '\'' +
                ", other_account_num='" + other_account_num + '\'' +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOther_account_num() {
        return other_account_num;
    }

    public void setOther_account_num(String other_account_num) {
        this.other_account_num = other_account_num;
    }

    public ShowPals(String nickname, String other_account_num) {

        this.nickname = nickname;
        this.other_account_num = other_account_num;
    }
}
