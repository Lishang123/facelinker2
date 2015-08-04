package com.xinxin.facelinker.domain;

/**
 * Created by gengliang on 2015/8/3.
 */
public class ShowToplist {
    private String account_num;
    private String photoUri;

    @Override
    public String toString() {
        return "ShowToplist{" +
                "account_num='" + account_num + '\'' +
                ", photoUri='" + photoUri + '\'' +
                '}';
    }

    public String getAccount_num() {
        return account_num;
    }

    public void setAccount_num(String account_num) {
        this.account_num = account_num;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public ShowToplist(String account_num, String photoUri) {

        this.account_num = account_num;
        this.photoUri = photoUri;
    }
}
