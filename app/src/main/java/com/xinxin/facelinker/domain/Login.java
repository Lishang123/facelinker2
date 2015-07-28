package com.xinxin.facelinker.domain;

/**
 * Created by gengliang on 2015/7/24.
 */
public class Login {
    private String token;
    private String my_account_num;

    @Override
    public String toString() {
        return "Login{" +
                "token='" + token + '\'' +
                ", my_account_num='" + my_account_num + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMy_account_num() {
        return my_account_num;
    }

    public void setMy_account_num(String my_account_num) {
        this.my_account_num = my_account_num;
    }

    public Login(String token, String my_account_num) {

        this.token = token;
        this.my_account_num = my_account_num;
    }
}
