/*
 * User: lin
 * Date:
 * Describe:添加我的动态
 *
 */

package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class AddMyMotionActivity extends Activity {

    private EditText etAddMyMotion;
    private Button btnAddMyMotion;
    private String my_account_num;

    HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_motion);

        etAddMyMotion= (EditText) findViewById(R.id.etAddMyMotion);
        btnAddMyMotion= (Button) findViewById(R.id.btnAddMyMotion);
        my_account_num=getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);

        btnAddMyMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etAddMyMotion.getText().toString().isEmpty()==true){
                    Toast.makeText(AddMyMotionActivity.this, R.string.add_my_motion_can_not_be_empty,Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(AddMyMotionActivity.this, getResources().getString(R.string.add_my_motion_connecting), getResources().getString(R.string.add_my_motion_connecting_to_server));
                RequestParams params = new RequestParams();
                //my_account_num, etAddMyMotion.getText().toString()
                params.addBodyParameter(Config.ACTION, Config.ACTION_ADD_MY_MOTION);
                params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                params.addBodyParameter(Config.KEY_MY_MOTION, etAddMyMotion.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        pd.dismiss();
                        Toast.makeText(AddMyMotionActivity.this, R.string.add_my_motion_success,Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        Toast.makeText(AddMyMotionActivity.this, R.string.add_my_motion_fail,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
