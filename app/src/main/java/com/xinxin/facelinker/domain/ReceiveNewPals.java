package com.xinxin.facelinker.domain;

import java.util.Arrays;

/**
 * Created by gengliang on 2015/7/25.
 */
public class ReceiveNewPals {
    private String[] other_account_num;

    @Override
    public String toString() {
        return "ReceiveNewPals{" +
                "other_account_num=" + Arrays.toString(other_account_num) +
                '}';
    }

    public String[] getOther_account_num() {
        return other_account_num;
    }

    public void setOther_account_num(String[] other_account_num) {
        this.other_account_num = other_account_num;
    }

    public ReceiveNewPals(String[] other_account_num) {

        this.other_account_num = other_account_num;
    }
}
