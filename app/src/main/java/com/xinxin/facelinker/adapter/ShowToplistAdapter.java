package com.xinxin.facelinker.adapter;

/**
 * Created by gengliang on 2015/8/3.
 */
public class ShowToplistAdapter {
    private String account_num;
    private String photoUri;

    @Override
    public String toString() {
        return "账户：" + account_num ;
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

    public ShowToplistAdapter(String account_num, String photoUri) {

        this.account_num = account_num;
        this.photoUri = photoUri;
    }
}
