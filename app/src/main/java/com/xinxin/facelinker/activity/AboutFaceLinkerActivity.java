package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.activity.util.SystemUiHider;
import com.xinxin.facelinker.domain.CreatorInfo;
import com.xinxin.facelinker.domain.UserProvision;
import com.xinxin.facelinker.domain.Version;
import com.xinxin.facelinker.utils.NetHelper;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class AboutFaceLinkerActivity extends Activity {
    LinearLayout ll_version_update;
    LinearLayout ll_version_info;
    LinearLayout ll_provision;
    LinearLayout ll_composer_info;

    Button left_title;
    HttpUtils httpUtils = new HttpUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_facelinker);
        ll_version_update = (LinearLayout) findViewById(R.id.ll_version_update);
        ll_version_info = (LinearLayout) findViewById(R.id.ll_version_info);
        ll_provision = (LinearLayout) findViewById(R.id.ll_provision);
        ll_composer_info = (LinearLayout) findViewById(R.id.ll_composer_info);

        left_title = (Button) findViewById(R.id.left_title);
        left_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_version_update:
                checkUpdate();
                break;
            case R.id.ll_version_info:
                getVsersionInfo();
                break;
            case R.id.ll_provision:
                getUserProvision();
                break;
            case R.id.ll_composer_info:
                getCreatorInfo();
                break;


        }


    }

    private void checkUpdate() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "check_update2");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                待写
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), getString(R.string.check_update_fail), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCreatorInfo() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "getCreatorInfo");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                CreatorInfo creatorInfo = (CreatorInfo) NetHelper.parseJsonData(responseInfo.result, CreatorInfo.class);
                Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("作者信息").setMessage(creatorInfo.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "获取作者信息失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserProvision() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "getUserProvision");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                UserProvision userProvision =
                        (UserProvision) NetHelper.parseJsonData(responseInfo.result, UserProvision.class);
                Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("用户条款").setMessage(userProvision.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "获取用户条款失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getVsersionInfo() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "check_update2");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Version version =
                        (Version) NetHelper.parseJsonData(responseInfo.result, Version.class);
                Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("用户条款").setMessage(version.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "获取用户条款失败", Toast.LENGTH_LONG).show();
            }
        });
    }


}
