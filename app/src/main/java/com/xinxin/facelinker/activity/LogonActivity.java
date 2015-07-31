package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.domain.GetLogonVerificationCode;
import com.xinxin.facelinker.utils.NetHelper;

public class LogonActivity extends Activity  {

    public EditText etPhone_Num;
    private EditText etPassword;
    private EditText etPassword_Again;
    private EditText etCheck;
    private Button btnLogon;
    private HttpUtils httpUtils = new HttpUtils();
    private Button btnCheck;
    private GetLogonVerificationCode getLogonVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        etPhone_Num = (EditText) findViewById(R.id.etPhone_num);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword_Again = (EditText) findViewById(R.id.etPassword_Again);
        etCheck = (EditText) findViewById(R.id.etCheck);
        btnLogon = (Button) findViewById(R.id.btnLogon);
        btnCheck= (Button) findViewById(R.id.btnCheck);

        btnLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etCheck.getText().toString().isEmpty() == true) {
                    Toast.makeText(LogonActivity.this, "未获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPhone_Num.getText().toString().length() != 11) {
                    Toast.makeText(LogonActivity.this, R.string.phone_num_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPassword.getText().toString().isEmpty() == true || etPassword_Again.getText().toString().isEmpty() == true) {
                    Toast.makeText(LogonActivity.this, R.string.password_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!etPassword.getText().toString().equals(etPassword_Again.getText().toString())) {
                    Toast.makeText(LogonActivity.this, R.string.password_again_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (!getLogonVerificationCode.getCode().equals(etCheck.getText().toString())) {
                        Toast.makeText(LogonActivity.this, R.string.verification_code_wrong, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    Toast.makeText(LogonActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                }

                final ProgressDialog pd = ProgressDialog.show(LogonActivity.this, getResources().getString(R.string.logon_connecting), getResources().getString(R.string.logon_connecting_to_server));
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_LOGON);
                params.addBodyParameter(Config.KEY_PHONE_NUM, etPhone_Num.getText().toString());
                params.addBodyParameter(Config.KEY_PASSWORD, etPassword.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        pd.dismiss();
                        Intent i = new Intent(LogonActivity.this, AddInfoActivity.class);
                        i.putExtra(Config.KEY_PHONE_NUM, etPhone_Num.getText().toString());
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        Toast.makeText(LogonActivity.this, R.string.logon_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPhone_Num.getText().toString().isEmpty()==true) {
                    Toast.makeText(LogonActivity.this, R.string.phone_num_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_GET_LOGON_VERIFICATION_CODE);
                params.addBodyParameter(Config.KEY_PHONE_NUM,etPhone_Num.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        getLogonVerificationCode=(GetLogonVerificationCode) NetHelper.parseJsonData(responseInfo.result, GetLogonVerificationCode.class);
                        Toast.makeText(LogonActivity.this,"获取验证码成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(LogonActivity.this,"获取验证码失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}

