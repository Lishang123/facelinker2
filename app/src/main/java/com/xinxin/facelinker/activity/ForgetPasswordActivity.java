/*
 * User: xinxin
 * Date:
 * Describe:忘记密码
 *
 */

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

public class ForgetPasswordActivity extends Activity {

    private EditText etPhone_num;
    private EditText etCode;
    private Button btnGetCode;
    private Button btnOk;
    private HttpUtils httpUtils = new HttpUtils();
    private GetLogonVerificationCode getLogonVerificationCode;
    private EditText etNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etPhone_num = (EditText) findViewById(R.id.etPhone_num);
        etCode = (EditText) findViewById(R.id.etCode);
        btnGetCode = (Button) findViewById(R.id.btnGetCode);
        btnOk = (Button) findViewById(R.id.btnOk);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etCode.getText().toString().isEmpty() == true) {
                    Toast.makeText(ForgetPasswordActivity.this, "未获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPhone_num.getText().toString().length() != 11) {
                    Toast.makeText(ForgetPasswordActivity.this, R.string.phone_num_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etNewPassword.getText().toString().isEmpty() == true) {
                    Toast.makeText(ForgetPasswordActivity.this, R.string.password_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (!getLogonVerificationCode.getCode().equals(etCode.getText().toString())) {
                        Toast.makeText(ForgetPasswordActivity.this, R.string.verification_code_wrong, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Toast.makeText(ForgetPasswordActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(ForgetPasswordActivity.this, getResources().getString(R.string.logon_connecting), getResources().getString(R.string.logon_connecting_to_server));
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_LOGON);
                params.addBodyParameter(Config.KEY_PHONE_NUM, etPhone_num.getText().toString());
                params.addBodyParameter(Config.KEY_PASSWORD, etNewPassword.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        pd.dismiss();
                        Intent i = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                        i.putExtra(Config.KEY_PHONE_NUM, etPhone_num.getText().toString());
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        Toast.makeText(ForgetPasswordActivity.this, R.string.set_new_password_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPhone_num.getText().toString().length() != 11) {
                    Toast.makeText(ForgetPasswordActivity.this, R.string.phone_num_wrong, Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_GET_LOGON_VERIFICATION_CODE);
                params.addBodyParameter(Config.KEY_PHONE_NUM, etPhone_num.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {

                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        getLogonVerificationCode = (GetLogonVerificationCode) NetHelper.parseJsonData(responseInfo.result, GetLogonVerificationCode.class);
                        Toast.makeText(ForgetPasswordActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(ForgetPasswordActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
