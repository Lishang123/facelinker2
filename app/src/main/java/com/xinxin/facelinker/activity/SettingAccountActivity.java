package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;

public class SettingAccountActivity extends Activity {
    Button left_title;
    TextView center_title;
    LinearLayout ll_change_person_info;
    LinearLayout ll_change_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        left_title = (Button) findViewById(R.id.left_title);
        center_title = (TextView) findViewById(R.id.center_title);
        ll_change_person_info = (LinearLayout) findViewById(R.id.ll_change_person_info);
        ll_change_password = (LinearLayout) findViewById(R.id.ll_change_password);
        left_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        center_title.setText("账号设置");
        ll_change_person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingPersonInfoActivity.class));
            }
        });
        ll_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("重新设置密码");
                EditText editText1 = new EditText(getApplicationContext());
                editText1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText1.setHint("请输入新密码");
                EditText editText2 = new EditText(getApplicationContext());
                editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText1.setHint("请再次输入新密码");
                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(editText1);
                linearLayout.addView(editText2);
                builder.setView(linearLayout);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText1.getText().equals(editText2.getText())) {
                            RequestParams params = new RequestParams();
                            params.addBodyParameter(Config.ACTION, Config.ACTION_CHANGE_FLUSER_PASSWORD);
                            params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_PASSWORD, editText1.getText().toString());
                            new HttpUtils().send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    if (responseInfo.result.equals("1")) {
                                        Toast.makeText(getApplicationContext(), "更改密码成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "更改密码失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                    Toast.makeText(getApplicationContext(), "修改密码网络服务器连接失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "两次密码不一致，更改密码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

            }
        });
    }


}
