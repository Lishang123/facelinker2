package com.xinxin.facelinker.domain;

/**
 * Created by gengliang on 2015/7/28.
 */
public class GetLogonVerificationCode {
    private String code;

    @Override
    public String toString() {
        return "GetLogonVerificationCode{" +
                "code='" + code + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GetLogonVerificationCode(String code) {

        this.code = code;
    }
}
