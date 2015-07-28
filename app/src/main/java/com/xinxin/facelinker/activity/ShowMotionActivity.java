package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.xinxin.facelinker.domain.ShowMotion;
import com.xinxin.facelinker.utils.NetHelper;

public class ShowMotionActivity extends Activity {

    private TextView tvAccountNum;
    private TextView tvNickname;
    private TextView tvMotion;
    private String motion_id;
    private TextView tvAddComment;
    private EditText etAddComment;
    private Button btnAddComment;
    private String my_account_num;
    private HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_motion);

        tvAccountNum = (TextView) findViewById(R.id.tvAccountNum);
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        tvMotion = (TextView) findViewById(R.id.tvMotion);
        tvAddComment = (TextView) findViewById(R.id.tvAddComment);
        etAddComment = (EditText) findViewById(R.id.etAddComment);
        btnAddComment = (Button) findViewById(R.id.btnAddComment);
        motion_id = getIntent().getStringExtra(Config.KEY_MOTION_ID);
        my_account_num = getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);

        final ProgressDialog pd = ProgressDialog.show(ShowMotionActivity.this, getResources().getString(R.string.show_motion_connecting), getResources().getString(R.string.show_motion_connecting_to_server));
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_MOTION);
        params.addBodyParameter(Config.KEY_MOTION_ID, motion_id);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ShowMotion showMotion =
                        (ShowMotion) NetHelper.parseJsonData(responseInfo.result, ShowMotion.class);

                pd.dismiss();
                tvAccountNum.setText(showMotion.getOther_account_num());
                tvNickname.setText(showMotion.getNickname());
                tvMotion.setText(showMotion.getMotion());
                for (int i = 0; i < showMotion.getComment().length; i++) {
                    tvAddComment.setText(tvAddComment.getText().toString() + showMotion.getComment()[i] + '\n');
                }

                btnAddComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (etAddComment.getText().toString().isEmpty() == true)
                            return;

                        RequestParams params = new RequestParams();
                        //my_account_num, tvAddComment.getText().toString(), motion_id
                        params.addBodyParameter(Config.ACTION, Config.ACTION_ADD_COMMENT);
                        params.addBodyParameter(Config.KEY_COMMENT, etAddComment.getText().toString());
                        params.addBodyParameter(Config.KEY_MOTION_ID, motion_id);

                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                tvAddComment.setText(tvAddComment.getText() + etAddComment.getText().toString() + '\n');
                                etAddComment.setText("");
                                Toast.makeText(ShowMotionActivity.this, R.string.add_comment_success, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(ShowMotionActivity.this, R.string.add_comment_fail, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(ShowMotionActivity.this, R.string.show_motion_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
